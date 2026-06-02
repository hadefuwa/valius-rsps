# CLAUDE.md — Valius RSPS (317, #181 OSRS data)

## What this is

Valius is a feature-rich 317-revision OSRS private server loading revision #181 OSRS data, developed over 16 months (released 2020). Java server + custom 317 client. Built with Gradle 5.5, Java 8.

Not an engine demo — this is a fully playable server with thousands of hours of custom content including ToB, Gauntlet, Hydra, Vorkath, Solak, CoX, Chambers, Wilderness revamp, and hundreds of unique custom/RS3 items.

## Project layout

```
Valius-Server-master/    Java game server (Gradle 5.5, Java 8)
Valius-Client-master/    Custom 317 Java client (Gradle)
cache.zip / extracted    Game cache (media_archives, sprites, settings)
modelencrypt.jar         Model encryption tool (do not distribute)
```

## Commands

```bat
:: Run server (from Valius-Server-master/)
run.bat               :: calls gradlew run then pauses

:: Or manually
cd Valius-Server-master
gradlew.bat run

:: Build client (from Valius-Client-master/)
cd Valius-Client-master
gradlew.bat build
```

## Architecture

**Language:** Java 8  
**Build:** Gradle 5.5  
**Main class:** `valius.Server`  
**Port:** 43594 (all server states: PUBLIC_PRIMARY, PUBLIC_SECONDARY, PRIVATE)  
**Package:** `valius.*`  
**Player saves:** `./Data/saves/` (flat files)  
**Group ironman saves:** `./Data/groups/`  
**Client version:** 5 (must match between client and server `Config.CLIENT_VERSION`)

## Key config (`Valius-Server-master/src/valius/Config.java`)

| Setting | Default | Notes |
|---|---|---|
| `local` | `false` | Set true for local dev |
| `SERVER_NAME` | `"Valius"` | Display name |
| `SERVER_STATE` | `PUBLIC_PRIMARY` | Controls port (always 43594) |
| `CLIENT_VERSION` | `5` | Must match client |
| `CHARACTER_SAVE_DIRECTORY` | `./Data/saves/` | Player save path |
| `DISCORD_BOT_TOKEN` | env var | Set `DISCORD_BOT_TOKEN` env var |

## Content highlights

### Raids & Endgame
- Theatre of Blood (near-identical to OSRS, shadow removed)
- Chamber of Xeric + Trials of Xeric (3 raids total)
- 100% Gauntlet with random map generation
- 100% Alchemical Hydra
- 100% Vorkath
- Nightmare boss

### Custom/Unique
- Solak boss
- Void Knight Champion
- 5 Wilderness event bosses (every 30-60 min)
- 2 Non-wilderness event bosses (every 1-2 hrs)
- Shooting Stars
- 7 new deep wilderness revenants + 30+ new items
- Starter Dungeon (5 custom NPCs, 20+ custom items)
- Hundreds of unique RS3/custom items and NPCs
- Unique textured items + weapon skins with perks
- Several custom maps

### Systems
- 100% Completionist cape with recoloring interface
- Group Ironman (up to 4 members)
- Currencies: Gold, Bloodmoney, PVM points, Boss points, Skill points
- Diaries & Achievements
- Daily reward system
- Rewritten clue scroll system
- Flash sales for donator shops
- Discord bot integration
- Christmas/Halloween/Easter/Thanksgiving holiday events
- Quest system (framework exists, no quests implemented)

### Client features
- Client control panel (type a secret code in login screen)
- Free-cam via client
- Inventory model viewer/editor ("item tool" in control panel)
- Text/sprites above models in-game
- Customizable camera + client background

## Unfinished content
- White Dragons (combat script done, needs drops)
- PvP Tournaments (base only)
- Several NPC models animated but not in-game (Grim Reaper, Warmonger, Skeleton Warlord)

## Notes
- Model encryption is active — cache models cannot be extracted/stolen
- `modelencrypt.jar` is the encryption tool (do NOT include decryption tool in repo)
- Discord bot tokens moved to environment variables (`DISCORD_BOT_TOKEN`, `DISCORD_BOT_TOKEN_LOCAL`)
- Cache extracted from `cache.zip` into `media_archives/`, `sprites/`, `settings/` at repo root
- Server expects cache relative to working directory when running
