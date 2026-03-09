param(
  [string]$EnvFile = "$PSScriptRoot\..\infra\.env"
)

if (!(Test-Path $EnvFile)) {
  throw "Env file not found: $EnvFile"
}

Get-Content $EnvFile | ForEach-Object {
  $line = $_.Trim()
  if ($line -eq "" -or $line.StartsWith("#")) { return }
  $parts = $line -split "=", 2
  if ($parts.Length -ne 2) { return }
  $name = $parts[0].Trim()
  $value = $parts[1].Trim()
  [System.Environment]::SetEnvironmentVariable($name, $value, "Process")
  Write-Host "Loaded $name"
}