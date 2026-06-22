# PvP Client v1.0.0 - Note di rilascio

**Versione Minecraft**: 26.1.2  
**Fabric Loader**: 0.19.3  
**Fabric API**: 0.151.0+26.1.2  
**Java**: JDK 25 (o compatibile)

## Funzionalità

### ✅ Implementato
- **Sistema HUD avanzato**: design modulare con contatori CPS, FPS e Keystrokes
- **Sistema di configurazione (GSON)**: config JSON persistente in `config/pvpclient_hud_config.json`
  - Impostazioni per modulo: posizione (x, y), colore, attivazione dello sfondo
  - Preset colore: Bianco, Rosso, Verde, Blu, Chroma (arcobaleno)
- **Migliorie visive**
  - Testo con ombra per tutti gli elementi HUD
  - Riquadri di sfondo semi-trasparenti con padding
  - Supporto chroma/arcobaleno
- **Tracker CPS**: conteggio click dei pulsanti sinistro/destro del mouse

### 🔄 Differito (compatibilità MC 26.1.2 in sospeso)
- Schermata dell’editor HUD (Screen API - richiede migrazione Fabric)
- Funzionalità Freelook / modifica prospettiva (richiede binding di rotazione entità corretti)
- Camera Mixin (richiede pattern di accesso ai campi della camera)

## Installazione

1. **Prerequisiti**
   - Minecraft Launcher (o launcher compatibile)
   - Java 25 o successivo
   - Fabric Loader 0.19.3

2. **Scarica le dipendenze**
   - Fabric Loader: https://fabricmc.net/use/installer/
   - Fabric API 0.151.0+26.1.2: https://modrinth.com/mod/fabric-api

3. **Installazione**
   - Metti `pvpclient-1.0.0.jar` in `%appdata%/.minecraft/mods/` (o equivalente)
   - Metti il JAR di Fabric API nella stessa cartella `mods`
   - Assicurati che Fabric Loader sia impostato come loader di gioco

4. **Primo avvio**
   - La mod genererà automaticamente `config/pvpclient_hud_config.json`
   - Configura i moduli HUD modificando manualmente il JSON (UI in sospeso)

## Esempio di configurazione

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

## Istruzioni di build

```bash
./gradlew build
```

Output: `build/libs/pvpclient-1.0.0.jar`

## Roadmap (v1.1+)

- [ ] Schermata editor HUD completa con drag-and-drop
- [ ] Freelook / modifica prospettiva (tasto Left ALT configurabile)
- [ ] Selettore colore UI nell'editor HUD
- [ ] Decoupling della camera tramite mixin adeguati
- [ ] Registrazione keybind (RSHIFT per editor, Left ALT per freelook)
- [ ] Integrazione eventi tick lato client

## Licenza

Vedi il file LICENSE nel repository.

## Autore

Davidix07TV

---

**Nota**: le funzionalità contrassegnate come "Differito" richiedono la migrazione alle API Fabric compatibili con Minecraft 26.1.2. Contributi benvenuti!
