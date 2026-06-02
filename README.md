# Valius RSPS

A feature-rich 317 RuneScape Private Server loading **#181 OSRS data**, developed over 16 months. Released as open source in 2020. Thousands of hours and dollars of development — fully playable out of the box.

---

## Quick Start

**Requirements:** Java 8 JDK (server + client), Java 11 JDK (Gradle build)

```bat
:: 1. Start the server
cd Valius-Server-master
gradlew.bat run

:: 2. Launch the client (in a separate terminal)
"C:\Program Files\Java\jre-1.8\bin\java.exe" -jar Valius-Client-master\build\libs\client.jar
```

The client reads its cache from `%USERPROFILE%\ValiusCache1\`. Extract `cache.zip` there on first run (see Setup below).

---

## Setup

### Server
The server runs immediately — no database required. Player saves go to `Valius-Server-master/Data/saves/`.

```bat
cd Valius-Server-master
gradlew.bat run
```

### Client — First Run
Extract `cache.zip` to `%USERPROFILE%\ValiusCache1\` and create a `cacheVersion.dat` file containing `1`:

```bat
mkdir "%USERPROFILE%\ValiusCache1"
:: Extract cache.zip contents into that folder
echo 1 > "%USERPROFILE%\ValiusCache1\cacheVersion.dat"
```

Then build and run:

```bat
cd Valius-Client-master
gradlew.bat jar
"C:\Program Files\Java\jre-1.8\bin\java.exe" -jar build\libs\client.jar
```

---

## Architecture

| Component | Language | Build | Entry point |
|---|---|---|---|
| Server | Java 8 | Gradle 5.5 | `valius.Server` |
| Client | Java 8 | Gradle 5.4 | `com.client.Client` |

**Server port:** 43594  
**Client connects to:** `127.0.0.1:43594` (local dev) / `192.99.145.49` (live)  
**Client version:** 5 (must match `Config.CLIENT_VERSION` on server)  
**Player saves:** `Valius-Server-master/Data/saves/` (flat files)  
**Cache location:** `%USERPROFILE%\ValiusCache1\`

---

## Content

### Raids & Major Bosses
- **Theatre of Blood** — near-identical to OSRS (shadow mechanic removed for accessibility)
- **Chambers of Xeric** + **Trials of Xeric** — 3 total raid instances
- **100% Gauntlet** — random map generation (reusable for custom raid design)
- **100% Alchemical Hydra**
- **100% Vorkath**
- **Nightmare of Ashihama**
- **Solak**, **Void Knight Champion** and more unique bosses

### World Events
- 5 Wilderness event bosses (spawn every 30–60 min)
- 2 Non-wilderness event bosses (spawn every 1–2 hrs)
- Shooting Stars
- Holiday events: Christmas, Halloween, Easter, Thanksgiving

### Custom & RS3 Content
- Hundreds of unique custom + RS3 items and NPCs
- Unique textured items
- Weapon skins with perks
- Several custom maps
- 7 new deep wilderness revenants + 30+ new items
- Starter Dungeon (5 custom NPCs, guide NPC, 20+ custom items)
- Wilderness Revamp with new bosses and revenants

### Systems
- **100% Completionist Cape** — full requirements + recoloring interface
- **Group Ironman** — up to 4 members
- **Currencies** — Gold, Bloodmoney, PVM Points, Boss Points, Skill Points
- **Diaries & Achievements**
- **Daily reward system**
- **Rewritten clue scroll system** — multiple clue types
- **Flash sales** for donator shops
- **Discord bot integration** (requires `DISCORD_BOT_TOKEN` env var)
- **Quest system** framework (no quests implemented — ready to build on)
- **Model encryption** — cache models cannot be extracted

### Client Features
- Client control panel (secret code on login screen)
- Free-cam mode
- Inventory model viewer/editor (`item tool` in control panel)
- Customizable camera + login background
- Smooth shading, tile blending, anti-aliasing options
- XP drops with configurable size, speed, colour

---

## Configuration

### Server — `Valius-Server-master/src/valius/Config.java`

| Setting | Default | Notes |
|---|---|---|
| `local` | `false` | Set `true` for local-only features |
| `SERVER_NAME` | `"Valius"` | Display name |
| `SERVER_STATE` | `PUBLIC_PRIMARY` | All states use port 43594 |
| `CLIENT_VERSION` | `5` | Must match client |

### Client — `Valius-Client-master/src/main/java/com/client/config/Configuration.java`

| Setting | Default | Notes |
|---|---|---|
| `IP` | `"127.0.0.1"` | Server address |
| `PORT` | `43594` | Game port |
| `CLIENT_VERSION` | `5` | Must match server |
| `CACHE_NAME` | `"ValiusCache1"` | Cache folder in user home |

### Discord Bot (optional)
Set these environment variables to enable the Discord integration:
```
DISCORD_BOT_TOKEN=your_live_bot_token
DISCORD_BOT_TOKEN_LOCAL=your_local_bot_token
```

---

## Unfinished / Notes

- **White Dragons** — combat script done, needs drop table and full setup
- **PvP Tournaments** — base framework only, not complete
- Several animated NPC models exist but aren't spawned in-game (Grim Reaper, Warmonger, Skeleton Warlord)
- `modelencrypt.jar` is the model **encryption** tool only — the decryption tool is not included
- Website/donation store files available separately (contact original author)
