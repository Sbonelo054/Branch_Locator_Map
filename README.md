
# Branch_Locator_Map

A modern Android application that helps users find nearby bank branches, view detailed branch information, and save favourites for quick access. The app uses Google Maps and location services to provide real-time branch discovery and navigation.

---

## Features

* View bank branches on an interactive Google Map
* Detect user location in real time
* Search branches by name
* View detailed branch information (address, phone, hours, services)
* Save and remove favourite branches
* See nearest branches in a bottom sheet
* Get directions using Google Maps navigation
* Call branch directly from the app
* Static informational screens (Help, Contact, Privacy Policy, Terms, Feedback)

---

## Screenshots

map_screenmap_searchbranch_detailsfavouritesempty_favouritesmore_screen


<img width="360" height="800" alt="1000132998" src="https://github.com/user-attachments/assets/99a5a66c-3fa5-4e37-9e3a-4671b0c41913" />

<img width="360" height="800" alt="1000132991" src="https://github.com/user-attachments/assets/fb9ce5b5-7e58-40aa-8dcf-0bfc76468331" />

<img width="360" height="800" alt="1000132996" src="https://github.com/user-attachments/assets/0064aa1a-c21b-440a-9265-48a60ccca94b" />

<img width="360" height="800" alt="1000132995" src="https://github.com/user-attachments/assets/5f37f4f0-1101-4c78-a76d-4cfdf0676d4a" />

<img width="360" height="800" alt="1000132997" src="https://github.com/user-attachments/assets/3fbcbe60-27da-4f97-8c9a-88b10143610f" />

<img width="360" height="800" alt="1000132999" src="https://github.com/user-attachments/assets/7f07b025-e276-4bfd-ba49-11a6234a20de" />


---

## Map Screen

The app provides a clean and interactive map interface:

* Google Map with custom bank markers
* Search bar for filtering branches
* My location button support
* Bottom sheet showing nearby branches if the location is turned on
* Tap bank marker to open branch details

---

## Branch Details Screen

Displays full information about a selected branch:

* Branch name and status (Open/Closed)
* Address and phone number
* Operating hours
* List of available services
* Favourite toggle button
* Actions:

  * Call branch
  * Open navigation in Google Maps

---

## Favourites Screen

The favourites section allows users to:

* View all saved branches
* Remove branches from favourites
* Quickly access branch details
* See empty state when no favourites exist

---

## More Screen

A settings-style menu that includes:

* Help & Support
* Contact Us
* Send Feedback
* Company Information
* Privacy Policy
* Terms of Service

---

## Architecture

The app follows **Clean Architecture principles** with clear separation of concerns.

### Layers

### Presentation Layer

* Jetpack Compose UI
* ViewModels (state management)
* UI State classes
* Navigation (Compose Navigation)

### Domain Layer

* Business models (`BankBranchDetail`)
* Repository interfaces
* Core business logic

### Data Layer

* Repository implementations
* Local data source (DAO)
* Data handling logic

---

## Key Components

* **MapsViewModel** → Handles map logic, search, and nearby branches
* **FavouritesViewModel** → Manages saved branches
* **BranchSharedViewModel** → Shares selected branch between screens
* **BankRepository** → Provides branch data
* **FavouritesRepository** → Handles local favourites storage

---

## Core Features Logic

### Search

Filters branches by name using reactive StateFlow:

* Empty query → no results shown
* Matching query → filtered list displayed

---

### Nearby Branches

* Uses user location (FusedLocationProvider)
* Calculates distance using Android Location API
* Sorts branches by proximity
* Displays top 3 nearest branches

---

### Favourites

* Stored locally using DAO
* Exposed as Flow
* Automatically updates UI when changed

---

## Technologies Used

* Kotlin
* Jetpack Compose
* Google Maps SDK
* Fused Location Provider API
* Kotlin Coroutines & Flow
* MVVM Architecture
* Koin (Dependency Injection)
* JUnit 5 + Coroutines Test + Flow, MockK (Unit Testing)
---

## Building the Project

### Prerequisites

* Android Studio Hedgehog (2023.1.1) or newer
* JDK 11 or newer
* Android SDK 34+

---

### Steps

Clone the repository:

```bash
git clone https://github.com/yourusername/BranchLocatorMap.git
```

Open in Android Studio:

* File → Open → Select project folder
* Wait for Gradle sync

Build the project:

```bash
Build > Make Project
```

---

## Running the App

### On Emulator

* Open AVD Manager
* Create a virtual device
* Select API 24+ system image
* Run the app (Shift + F10)

---

### On Physical Device

* Enable Developer Mode
* Enable USB Debugging
* Connect device via USB
* Run app from Android Studio

---

## Testing

The project includes unit tests for repository logic.

---

### Running Tests

From Android Studio:

* Right-click test file → Run

From terminal:

```bash
./gradlew test
```

---

### Test Coverage

* Bank repository logic
* Favourites repository operations
* Mocked DAO interactions
* Flow-based assertions

---

## License

This project is licensed under the MIT License.

---

## Acknowledgments

* Google Maps SDK
* Jetpack Compose
* Material 3 Design System
* Kotlin Coroutines

---
