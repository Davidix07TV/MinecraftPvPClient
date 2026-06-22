# PvP Client v1.0.0 - Release Notes

**Minecraft Version**: 26.1.2  
**Fabric Loader**: 0.19.3  
**Fabric API**: 0.151.0+26.1.2  
**Java**: JDK 25 (or compatible)

## Features

### ✅ Implemented
- **Advanced HUD System**: Modular design with CPS, FPS, and Keystrokes counters
- **Configuration System (GSON)**: Persistent JSON config at `config/pvpclient_hud_config.json`
  - Per-module settings: position (x, y), color, background toggle
  - Color presets: White, Red, Green, Blue, Chroma (rainbow)
- **Visual Enhancements**
  - Drop-shadow text rendering for all HUD elements
  - Semi-transparent background boxes with padding
  - Chroma/rainbow color cycling support
- **CPS Tracker**: Left/Right mouse button click counting

### 🔄 Deferred (MC 26.1.2 Compatibility Pending)
- HUD Editor Screen (Screen API - requires Fabric screen migration)
- Freelook Feature / Perspective Mod (requires proper entity rotation method bindings)
- Camera Mixin (requires Camera field access patterns)

## Installation

1. **Prerequisites**
   - Minecraft Launcher (or compatible launcher)
   - Java 25 or later
   - Fabric Loader 0.19.3

2. **Download Dependencies**
   - Fabric Loader: https://fabricmc.net/use/installer/
   - Fabric API 0.151.0+26.1.2: https://modrinth.com/mod/fabric-api

3. **Install**
   - Place `pvpclient-1.0.0.jar` in `%appdata%/.minecraft/mods/` (or equivalent)
   - Place Fabric API JAR in the same mods folder
   - Ensure Fabric Loader is set as the game loader

4. **First Launch**
   - The mod will auto-generate `config/pvpclient_hud_config.json`
   - Configure HUD modules via manual JSON editing (UI pending implementation)

## Configuration Example

```json
{
  "fps": {
    "enabled": true,
    "x": 4,
    "y": 4,
    "textColor": 16777215,
    "chroma": false,
    "backgroundEnabled": true
  },
  "cps": {
    "enabled": true,
    "x": 4,
    "y": 22,
    "textColor": 16777215,
    "chroma": false,
    "backgroundEnabled": true
  },
  "keystrokes": {
    "enabled": true,
    "x": 4,
    "y": 50,
    "textColor": 16777215,
    "chroma": false,
    "backgroundEnabled": true
  },
  "disableParticles": false,
  "disableWeather": false,
  "disableRainParticles": false
}
```

## Build Instructions

```bash
./gradlew build
```

Output: `build/libs/pvpclient-1.0.0.jar`

## Roadmap (v1.1+)

- [ ] Full HUD Editor Screen with drag-and-drop positioning
- [ ] Freelook / Perspective Mod (Left ALT keybind, configurable)
- [ ] Color picker UI in HUD editor
- [ ] Camera decoupling via proper mixins
- [ ] Keybind registration (RSHIFT for editor, Left ALT for freelook)
- [ ] Client tick event integration

## License

See LICENSE file in repository.

## Author

Davidix07TV

---

**Note**: Features marked as "Deferred" require migration to Minecraft 26.1.2-compatible Fabric APIs. Contributions welcome!
