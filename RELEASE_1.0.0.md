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
- **Schermata dell'editor HUD** (beta): interfaccia di posizionamento drag-and-drop per i moduli HUD, migrata alle API Screen moderne di Minecraft 26.1.2
- **Funzionalità Freelook** (beta): toggle configurabile (predefinito Left ALT) per visuale libera indipendente; non modifica l'orientamento del giocatore
- **Camera Mixin e Player Mixin**: implementati pattern di accesso ai campi della camera (`xRot`/`yRot`) e metodi di rotazione (`turn(double,double)`) per supportare Freelook

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

- [ ] Test in-game completo e bug fix per editor HUD e freelook
- [ ] Indicatore HUD visuale per stato Freelook (on/off)
- [ ] Selettore colore UI integrato nell'editor HUD (attualmente configurazione JSON)
- [ ] Snap-to-grid e guide visive per posizionamento moduli
- [ ] Export/import preset layout HUD
- [ ] Aggiunta più preset colore (gradients, animazioni)
- [ ] Compatibilità versioni Minecraft future (adattamento API)

## Licenza

Vedi il file LICENSE nel repository.

## Autore

Davidix07TV

---

**Note di rilascio**: v1.0.0 include l'implementazione completa del sistema HUD, freelook e camera mixin per Minecraft 26.1.2. Le funzionalità sono state migrate alle API Fabric compatibili e testate a livello di compilazione. **Test in-game consigliato** per verificare il comportamento visuale di drag-and-drop dell'editor HUD e la rotazione della camera in modalità Freelook. Segnala bug o suggerimenti aprendo un issue.
