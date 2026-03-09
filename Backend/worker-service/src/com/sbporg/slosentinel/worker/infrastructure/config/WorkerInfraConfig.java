package com.sbporg.slosentinel.worker.infrastructure.config;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
public class WorkerInfraConfig {

  @Bean
  public WebClient targetsWebClient(SentinelApiProperties props) {
    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) props.timeoutMs())
            .responseTimeout(Duration.ofMillis(props.timeoutMs()))
            .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(props.timeoutMs(), TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(props.timeoutMs(), TimeUnit.MILLISECONDS)));

    return WebClient.builder()
            .baseUrl(props.baseUrl())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
  }

  @Bean(name = "checkExecutor")
  public TaskExecutor checkExecutor(WorkerProperties props) {
      ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
      exec.setCorePoolSize(Math.max(2, props.maxConcurrency()));
      exec.setMaxPoolSize(Math.max(2, props.maxConcurrency()));
      exec.setQueueCapacity(200);
      exec.setThreadNamePrefix("check-");
      exec.initialize();
      return exec;
  }
  
}
