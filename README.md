# OrbVault

OrbVault is an Android application for exploring countries and geographic data, developed in Kotlin with Jetpack Compose.

I developed this project to put into practice API consumption, state management, and building declarative interfaces with Jetpack Compose.

- [Technologies](#technologies-used)  
- [Features](#features)  
- [Preview](#preview)  
- [Technical Decisions](#technical-decisions)
- [Biggest Challenge](#biggest-challenge)
- [Project Goal](#project-goal)  
- [Contact](#contact)  
- [Disclaimer](#disclaimer)  

## Technologies used

- [Kotlin](https://kotlinlang.org/) — Language used in development (my favorite 💜)
- [Jetpack Compose](https://developer.android.com/jetpack/compose) — Fast and simple UI creation
- [Material 3](https://m3.material.io/) — Used to maintain visual consistency and good UI practices
- [Retrofit](https://square.github.io/retrofit/) — HTTP client for REST API consumption
- [Room](https://developer.android.com/training/data-storage/room) — Local data persistence used to save favorite countries with support for SQLite queries
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) — Local data persistence used to save user preferences
- [Koin](https://insert-koin.io/) — Used to manage app dependencies in a more organized and simple way
- [Coil](https://coil-kt.github.io/coil/compose/) — Asynchronous image loading via URL (AsyncImage)
- [Navigation](https://developer.android.com/jetpack/compose/navigation) — Navigation management between screens
- MVVM Architecture — Project organization using ViewModel and StateFlow for state management and separation of responsibilities
- Text management with `strings.xml` — Centralization of app texts, facilitating maintenance and future translations

## Features

### Countries
* Search for countries by name or code (cca3)
  * Quick access to recently visited countries (max. 5)
* Display of detailed country information:
  * Common and official name  
  * Capital  
  * Region and subregion  
  * Language  
  * Currency  
  * Formatted population  
  * Time zones  
* Display of the country flag  
* Country quick actions:
  * View Weather (next feature)
  * View on Map  
  * View Neighbors  
* Neighbor Countries
  * Display of neighboring countries (max. 5)  
  * Navigation when clicking on a neighboring country  

### Weather
* Real-time weather query by country  
* Display of:
  * Current temperature and feels like  
  * Humidity and wind  
  * Weather condition (clear sky, rain, etc.)  
* Integration with Open-Meteo API using multi-service architecture  

### Explore
* Top 10 most populous countries (by population)
* Top 10 largest countries (by area)
* Browse all countries catalog with lazy pagination
* Random country with the possibility of generating new results  
* Display of details in BottomSheet for a better navigation experience without leaving the main screen  
* Navigation between different exploration categories  

#### Global Statistics
* Total number of countries, population, and global area  
* Highlights:
  * Most populous country  
  * Largest country by area  
* Opening details via BottomSheet (without navigation)  

#### Upcoming (Explore)
* Countries by Region (In Development)
  * Exploration of countries organized by continent  
* Country comparison (In Development)
  * Comparison of data between up to 2 countries  
* Geographic quiz  
  * Dynamic questions using real country data (area and population)  
  * Feedback for correct, incorrect, and expired answers 
  * Timed questions with a 15-second countdown  
  * Timeout state for unanswered questions  
  * Final result with the user's score
  * Best score with persistence using DataStore
* Quick access to favorite countries  

### Favorites
* List of favorited countries with local persistence (Room)  
* Removal by swipe, with undo option via Snackbar  
* Empty favorites screen with button to explore new countries  
* Favorite ordering automatically maintained persistently  

### General
* Handling of loading and error states with retry option  
* Dynamic navigation, adjusting TopBar and BottomBar according to the current screen  
* Specific titles on each screen to facilitate user orientation  
* Theme support (`Dark`, `Light` and `System`) with persistence using DataStore  
* Synchronization with:
  * Status Bar  
  * Navigation Bar  
* Internationalization system (i18n):
  * Support for multiple languages (English, Spanish and Portuguese)  
  * Persistence of user choice via DataStore  
  * Automatic detection of device language with fallback to `English`  

## Preview
### Main Screens
<img src="https://github.com/user-attachments/assets/43d0f603-b51a-4ca2-a049-890440b2d7e6" width="240"/>
<img src="https://github.com/user-attachments/assets/391be294-c7be-4445-acfc-9f1da4b3bfe9" width="240"/>
<img src="https://github.com/user-attachments/assets/a7c4bb4f-2fad-411b-8d7e-c30ba8bdc116" width="240"/>
<img src="https://github.com/user-attachments/assets/d99c91b3-ac65-4e22-b479-6ffa56ccd7f5" width="240"/>

### Quiz
<img src="https://github.com/user-attachments/assets/22fce133-c07c-4940-83c5-734b25129dc5" width="240"/>
<img src="https://github.com/user-attachments/assets/6132a331-d435-4f79-8da0-d378780a775b" width="240"/>
<img src="https://github.com/user-attachments/assets/04a45ab2-ee58-45c0-9f19-3242f2f62ff9" width="240"/>
<img src="https://github.com/user-attachments/assets/323d12da-ee27-41e2-9a53-6a6139cc7a72" width="240"/>

Below I will provide a video showing how the application looks:  
> https://github.com/user-attachments/assets/48a7068d-cf5c-4e0f-af6e-d5540b95c5fc

## Technical Decisions

### Jetpack Compose
I chose Jetpack Compose because I already had experience with the declarative approach, which makes UI construction more organized. I honestly don't miss XML ;)

### Architecture (MVVM)
I chose to structure the project using MVVM to keep the logic separated from the UI.  
The use of ViewModel with StateFlow allows the UI to automatically react to state changes.  

> The project evolved into a domain based separation (Countries and Weather), reducing coupling between features/screens.

### API Consumption
Country and weather data are obtained through public APIs. This allowed me to practice HTTP requests, data handling, and layer organization within the project.

- Countries API (RestCountries) used for listing and country details  
- Weather API (Open-Meteo) used for real-time weather data by location  

I applied optimizations such as:
* Use of `fields` to reduce payload  
* Limitation of languages and time zones  
* Limitation of displayed neighbors (max. 5)  

> Country search was optimized to support search by name or code (CCA3)

> 🔗 API Link (RestCountries): https://restcountries.com/  
> 🔗 API Link (Open-Meteo): https://open-meteo.com/

### Data Persistence (Room + DataStore)
I used Room for structured data (favorites) and DataStore for user preferences (theme, language and quiz `bestScore`)

### UI State Management (UiState)
To handle screen states (loading, success, and error), I used an approach with sealed class (UiState) along with StateFlow.  
With this, the UI automatically reacts to state changes, making the code more organized and easier to understand.

### Image Loading
I used the Coil library to load flag images via URL asynchronously, ensuring a smoother experience and better performance in image rendering.

### Material 3
Material 3 was chosen to maintain a modern and consistent visual design, taking advantage of well-structured components.

### Screen Navigation (Navigation)
To organize navigation between screens, I used Navigation. This library greatly simplifies route definition and navigation management.

## Biggest Challenge

Using **Room** was a bit complicated at first, since I had more experience with DataStore. I had to learn entities, DAOs, and migrations, which helped me better understand when to use a relational database.  
> I really liked the advantages of Room, it will definitely be my preference in future projects  

## Project Goal

This project was developed with the goal of:

* Practicing REST API consumption  
* Improving UI development using Jetpack Compose  
* Improving code organization with MVVM  
* Improving state management using ViewModel and StateFlow  
* Building a portfolio project  

## Contact

🔗 [LinkedIn](https://www.linkedin.com/in/gblrodrigues/)

## Disclaimer

This project was developed exclusively for educational and portfolio purposes.

The data used in this application is provided by public APIs:  
* Countries: https://restcountries.com/  
* Weather: https://open-meteo.com/
