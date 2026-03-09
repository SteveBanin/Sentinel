package com.sbporg.slosentinel.api.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateTargetRequest(

        @NotBlank(message = "name is required")
        @Size(min = 2, max = 80, message = "name must be between 2 and 80 characters")
        String name,

        // @Pattern(
        //         regexp = "^(HTTP|TCP|DNS)$",
        //         message = "type must be one of: HTTP, TCP, DNS"
        // )
        // String type,
        @NotBlank(message = "type is required")
        @Pattern(
        regexp = "^(?i)(HTTP|TCP|DNS)$",
        message = "type must be one of: HTTP, TCP, DNS"
        )
        String type,

        @NotBlank(message = "endpoint is required")
        @Size(min = 3, max = 2048, message = "endpoint must be between 3 and 2048 characters")
        String endpoint,

        @NotNull(message = "intervalSeconds is required")
        @Min(value = 5, message = "intervalSeconds must be at least 5 seconds")
        Long intervalSeconds

) {}