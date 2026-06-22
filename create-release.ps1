#!/usr/bin/env pwsh
# GitHub Release Creator for PvP Client v1.0.0
# Usage: .\create-release.ps1 -GitHubToken <your_token>

param(
    [Parameter(Mandatory=$true)]
    [string]$GitHubToken
)

$owner = "Davidix07TV"
$repo = "MinecraftPvPClient"
$tag = "v1.0.0"
$jarFile = "build/libs/pvpclient-1.0.0.jar"
$apiUrl = "https://api.github.com/repos/$owner/$repo/releases"

$releaseNotes = @"
# PvP Client v1.0.0

## Features Implemented
- Advanced HUD System (CPS, FPS, Keystrokes)
- Configuration System (GSON + JSON persistence)
- Visual Enhancements (drop-shadow, backgrounds, color presets)
- CPS Tracker (left/right click counting)

## Requirements
- **Minecraft**: 26.1.2
- **Fabric Loader**: 0.19.3
- **Fabric API**: 0.151.0+26.1.2
- **Java**: JDK 25+

## Installation
1. Download and install Fabric Loader 0.19.3
2. Place the JAR in `%appdata%/.minecraft/mods/`
3. Install Fabric API to the same folder
4. Launch the game with Fabric loader

## Configuration
The mod auto-generates `config/pvpclient_hud_config.json` on first launch. Configure via JSON editing or (when implemented) via in-game HUD editor.

## Deferred Features
- HUD Editor Screen (Screen API)
- Freelook / Perspective Mod (Entity rotation bindings)
- Keybind registration
"@

# Create release via GitHub API
$headers = @{
    "Authorization" = "Bearer $GitHubToken"
    "Accept"        = "application/vnd.github+json"
}

$body = @{
    tag_name             = $tag
    target_commitish     = "main"
    name                 = "v1.0.0 - PvP Client Release"
    body                 = $releaseNotes
    draft                = $false
    prerelease           = $false
} | ConvertTo-Json

Write-Host "Creating GitHub Release for $tag..."
$response = Invoke-WebRequest -Uri $apiUrl `
    -Method POST `
    -Headers $headers `
    -ContentType "application/json" `
    -Body $body

if ($response.StatusCode -eq 201) {
    Write-Host "✅ Release created successfully!"
    $releaseData = $response.Content | ConvertFrom-Json
    $uploadUrl = $releaseData.upload_url -replace '\{.*\}', ''

    Write-Host "Uploading JAR file..."
    $jarFileFullPath = Resolve-Path $jarFile
    Invoke-WebRequest -Uri "$uploadUrl?name=pvpclient-1.0.0.jar" `
        -Method POST `
        -Headers $headers `
        -ContentType "application/java-archive" `
        -InFile $jarFileFullPath

    Write-Host "✅ JAR uploaded!"
    Write-Host "Release URL: $($releaseData.html_url)"
} else {
    Write-Host "❌ Failed to create release: $($response.StatusCode)"
    Write-Host $response.Content
}
