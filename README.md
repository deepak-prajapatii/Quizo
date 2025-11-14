# Project README

## Overview
Minimal, modular Android application for a quiz domain. Emphasizes clear separation between business rules and platform code to improve testability and maintainability.

## Recommended IDE
- Android Studio Narwhal 3 Feature Drop \| 2025.1.3

## Tech stack
- Languages: Kotlin
- Build system: Gradle (use the project Gradle wrapper)
- Target: Android SDK (configured per-module in `build.gradle.kts`)

## Architecture 
- Pattern: Clean / Hexagonal-inspired modular architecture.
- Goal: Keep business logic platform-agnostic and isolate Android specifics to UI and framework modules.
- Layers:
    - `domain` — core business models and use case interfaces (pure Kotlin/Java, no Android dependencies)
    - `data` — repository implementations, data sources, mappers (implements `domain` interfaces)
    - `presentation` / `app` — Android UI, ViewModels, activities/fragments and DI wiring

## Module boundaries & communication
- Dependencies flow inward: `presentation` → `data` → `domain` (consumers depend on abstractions in `domain`).
- Use interface-driven contracts and dependency injection to allow swapping implementations and easy testing.

## Project layout
- Root: `settings.gradle` / `settings.gradle.kts`, root `build.gradle` files
- Modules: `app/`, `domain/`, `data/` (additional feature or library modules as needed)

## Build & run
- Open the project in Android Studio Narwhal 3 Feature Drop \| 2025.1.3.
- Build from terminal using the wrapper:
    - `./gradlew assembleDebug`
    - Install/run on device: `./gradlew installDebug` or use Android Studio run configurations

## Contributing
- Respect module boundaries and dependency directions.
- Keep business logic in `domain`. Place Android- and framework-specific code in `app`/`presentation`.
- Write unit tests for core use cases and integration tests where appropriate.

