# Rick and Morty - Kotlin Multiplatform

A Kotlin Multiplatform (KMP) project that consumes the [Rick and Morty API](https://rickandmortyapi.com/) to display characters, locations, and episodes. Built with **Compose Multiplatform**, targeting Android and iOS.

## 🚀 Features

- **Characters**: Explore the cast of Rick and Morty.
  - Search characters by name.
  - View status, species, and images.
- **Locations**: Discover the various settings in the multiverse.
- **Episodes**: Keep track of all episodes and their air dates.
- **Shared UI**: 100% Shared UI using Compose Multiplatform.
- **Modern Tech Stack**: Uses the latest libraries for networking, serialization, and image loading.

## 🛠 Tech Stack

- **Compose Multiplatform**: For building the shared UI on Android and iOS.
- **Ktor**: Multiplatform HTTP client for networking.
- **Kotlinx Serialization**: For parsing JSON responses.
- **KMP-ViewModel**: Shared ViewModels for managing state.
- **Coil3**: Multiplatform image loading.
- **Material 3**: For a modern and clean design.

## 📁 Project Structure

- `shared`: Contains the core logic and UI shared between platforms.
  - `commonMain`: Shared code (API, Models, ViewModels, and Compose UI).
  - `androidMain`: Android-specific implementations.
  - `iosMain`: iOS-specific implementations.
- `androidApp`: Entry point for the Android application.
- `iosApp`: Entry point for the iOS application.

## ⚙️ Requirements

- **Android Studio Ladybug** or newer.
- **Xcode 15+** (for iOS).
- **Kotlin 2.0.0+**.

## 🏃 Running the Project

### Android
1. Open the project in Android Studio.
2. Select the `androidApp` configuration.
3. Click **Run**.

### iOS
1. Open the project in Android Studio.
2. Ensure you have an iOS Simulator or Device configured.
3. Select the `iosApp` configuration.
4. Click **Run**.
*(Alternatively, open the `iosApp` folder in Xcode and run from there).*

## 🧪 Running Tests

- **Shared Tests**: `./gradlew :shared:allTests`
- **Android Tests**: `./gradlew :shared:testAndroidHostTest`
- **iOS Tests**: `./gradlew :shared:iosSimulatorArm64Test`

---
*Created as a demonstration of Compose Multiplatform capabilities.*
