# CryptoTracker 🪙

A modern Android application for tracking cryptocurrency prices in real-time, built with the latest Android technologies and Clean Architecture principles.

---

## Screenshots

<p align="center">
  <img src="/art/sc_01.png" width="220"/>
  <img src="/art/sc_02.png" width="220"/>
</p>
<p align="center">
   <img src="/art/sc_03.png" width="220"/>
  <img src="/art/sc_04.png" width="220"/>
</p>

---

## Features

- 📈 Real-time cryptocurrency prices from CoinGecko API
- 🔍 Offline search with client-side filtering
- 💾 Local cache with Room database (Single Source of Truth)
- 📊 24h price change with color indicators (green/red)
- 📱 Clean and modern UI built with Jetpack Compose
- 🌙 Dark and Light theme support

---

## Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose + Material3 |
| **Architecture** | Clean Architecture + MVI |
| **Dependency Injection** | Hilt |
| **Async** | Coroutines + Flow |
| **Networking** | Retrofit + OkHttp |
| **Local Database** | Room |
| **Image Loading** | Coil |
| **Navigation** | Compose Navigation |
| **Testing** | JUnit + Mockk + Turbine |

---

## Architecture

This project follows **Clean Architecture** principles with three main layers:

```
app/
├── data/
│   ├── local/          # Room database, DAOs, Entities
│   ├── remote/         # Retrofit API service, DTOs
│   ├── repository/     # Repository implementations, Mappers
│   └── di/             # Hilt modules (Network, Database, Repository)
├── domain/
│   ├── model/          # Domain models (Coin, CoinDetail, Resource)
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Use cases (GetCoinsUseCase, GetCoinDetailUseCase)
└── presentation/
    ├── ui/
    │   ├── screen/     # Composable screens (CoinList, CoinDetail)
    │   └── theme/      # Material3 theme, colors, typography
    └── viewmodel/      # ViewModels with StateFlow
```

### Architecture Diagram

```
   API (CoinGecko)
        ↓
   Retrofit (DTO)
        ↓
   Repository ──→ Room (Cache)
        ↓
   UseCase (Resource<T>)
        ↓
   ViewModel (StateFlow)
        ↓
   Compose UI
```

---

## Key Concepts

### Clean Architecture
Each layer has a single responsibility and depends only on the layer below it. The `domain` layer has zero Android dependencies — it's pure Kotlin.

### Single Source of Truth
Data always flows from the local Room database to the UI. The network is only used to update the database, never the UI directly.

### Resource Sealed Class
All UI states are wrapped in a `Resource<T>` sealed class:
```kotlin
sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>()
    class Error<T>(message: String) : Resource<T>()
}
```

### Offline Search
Search is performed client-side using Kotlin's `combine` operator on `StateFlow`, allowing instant filtering without any network requests.

---

## API

This app uses the [CoinGecko API](https://www.coingecko.com/en/api) (free tier):

- `GET /coins/markets` — List of coins with market data
- `GET /coins/{id}` — Detailed info for a specific coin

> **Note:** The free tier has rate limits. Search is handled client-side to minimize API calls.

---

## Getting Started

### Prerequisites
- Android Studio Ladybug or newer
- JDK 11+
- Android SDK 24+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/mehrankasebvatan/Crypto.git
```

2. Open the project in Android Studio

3. Build and run on an emulator or device (API 24+)

> No API key required — CoinGecko free tier works out of the box.

---

## Testing

Run unit tests:
```bash
./gradlew test
```

Test coverage includes:
- `GetCoinsUseCase` — verifies Loading → Success and Loading → Error states
- `CoinListViewModel` — verifies state management and search filtering
- `FakeCoinRepository` pattern for isolated testing

---

## Dependencies

```toml
# Core
kotlin = "2.3.21"
agp = "9.0.0"

# DI
hilt = "2.59.2"

# Network
retrofit = "3.0.0"
okhttp = "5.3.2"

# Database
room = "2.7.1"

# UI
composeBom = "2024.09.00"
coil = "2.7.0"
navigation = "2.7.7"

# Async
coroutines = "1.11.0"

# Testing
mockk = "1.14.9"
turbine = "1.2.1"
```

---

## License

```
MIT License

Copyright (c) 2026

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```
