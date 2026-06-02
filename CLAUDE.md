# CLAUDE.md — Valius RSPS (317, #181 OSRS data)

## What this is

Valius is a fully playable 317 OSRS private server loading revision #181 data. Java 8, Gradle 5.x. Built with Lombok and JavaFX. Released as open source in 2020 after ~16 months of active development.

## Commands

```bat
:: Build and run server (Java 11 for Gradle, targets Java 8)
cd Valius-Server-master
set JAVA_HOME=C:\Program Files\Amazon Corretto\jdk11.0.29_7
gradlew.bat run

:: Build client
cd Valius-Client-master
set JAVA_HOME=C:\Program Files\Amazon Corretto\jdk11.0.29_7
gradlew.bat jar

:: Run client (must use Java 8 — JavaFX bundled)
"C:\Program Files\Java\jre-1.8\bin\java.exe" -jar Valius-Client-master\build\libs\client.jar
```

## Architecture

**Language:** Java 8  
**Build:** Gradle 5.5 (server), Gradle 5.4 (client)  
**Server main:** `valius.Server`  
**Client main:** `com.client.Client`  
**Port:** 43594  
**Package:** `valius.*` (server), `com.client.*` (client)  
**Player saves:** `Valius-Server-master/Data/saves/` (flat files per player)  
**Client cache:** `%USERPROFILE%\ValiusCache1\` (extracted from `cache.zip`)

## Key config files

| File | Purpose |
|---|---|
| `Valius-Server-master/src/valius/Config.java` | Server settings — name, port, state, game flags |
| `Valius-Server-master/src/valius/ServerState.java` | Port definitions (all 43594) |
| `Valius-Client-master/src/main/java/com/client/config/Configuration.java` | Client IP, port, cache name, version |

## Build notes

### Java version requirements
- **Gradle daemon:** Java 11 (Corretto at `C:\Program Files\Amazon Corretto\jdk11.0.29_7`)
- **Client runtime:** Must use **Java 8** (`C:\Program Files\Java\jre-1.8\`) — JavaFX is bundled in JDK 8's `jfxrt.jar` and was removed in JDK 9+
- The client `build.gradle` references `jfxrt.jar` directly from JRE 8

### Dependency fixes applied
- `jcenter()` replaced with `mavenCentral()` — jcenter is shut down
- `net.runelite.pushingpixels:trident:1.5.00` — downloaded to `Valius-Client-master/libs/`
- `net.runelite.pushingpixels:substance:8.0.02` — downloaded to `Valius-Client-master/libs/`
- `club.minnced:java-discord-rpc` — stubbed out (see Discord section below)
- `org.tritonus:tritonus-dsp` excluded from Discord4J (audio lib, not needed)
- ProGuard obfuscation disabled for local dev (`jar.finalizedBy(myProguardTask)` commented out)

## Discord integration

Discord calls are disabled for local dev:
- `RPC.init()` in `Client.java` is commented out
- `DiscordBot.sendMessage()` returns early if `cli == null` (no bot token set)
- Bot is never initialized at server startup — only via in-game `::` command
- To enable: set `DISCORD_BOT_TOKEN` and `DISCORD_BOT_TOKEN_LOCAL` environment variables

## Client cache setup

The client looks for its cache at `%USERPROFILE%\ValiusCache1\`. On first run:
1. Extract `cache.zip` from repo root into that directory
2. Create `cacheVersion.dat` containing `1`
3. The client will skip the download if `cacheVersion.dat` exists with matching version

`Configuration.CACHE_VERSION_LOADER = 1` is what the client checks against.

## Content locations

| Content | Location |
|---|---|
| Server scripts/content | `Valius-Server-master/src/valius/content/` |
| NPC combat scripts | `Valius-Server-master/src/valius/model/entity/npc/impl/` |
| Item definitions | `Valius-Server-master/src/valius/model/definitions/` |
| Packet handlers | `Valius-Server-master/src/valius/net/packet/impl/` |
| Game config flags | `Valius-Server-master/src/valius/Config.java` |
| Client UI screens | `Valius-Client-master/src/main/java/com/client/` |
| Client graphics | `Valius-Client-master/src/main/java/com/client/cache/graphics/` |

## Common pitfalls

- **Client won't launch** — must use Java 8, not 11+. JavaFX (`jfxrt.jar`) is only in JDK 8.
- **Cache error on startup** — extract `cache.zip` to `%USERPROFILE%\ValiusCache1\` and create `cacheVersion.dat` with value `1`
- **Client version mismatch** — `Config.CLIENT_VERSION` (server) must equal `Configuration.CLIENT_VERSION` (client), both are `5`
- **Gradle build fails with jcenter error** — jcenter is dead; `build.gradle` now uses `mavenCentral()`
- **Discord errors in console** — expected if no `DISCORD_BOT_TOKEN` env var is set; all calls are safely no-op'd
- **Player saves** are in `Data/saves/` as text files — edit directly to adjust stats, items, etc.
