## ABOUT 

This is a sample app to demonstrate the standardized architecture used across Android apps.

# Features

- The app displays top 20 users from StackOverflow
- Users have their name, image, reputation and badge count displayed for flavor
- Fetched users can be followed or unfollowed, and the state is persisted in a local storage
- If the server is unavailable, the app will display an empty error state
- The repository code is covered by unit tests that test all paths of the code, including error handling and edge cases

# Architecture

Flow of data looks like this:

```UI <-> ViewModel <-> Repository <-> Network/Database```

The architecture is based on the **MVVM** pattern, with a clear separation of concerns between the UI, ViewModel, and Repository layers. The reason for this decision is to ensure that the app is maintainable, testable, and scalable.

***User Interface***

UI is built using Jetpack Compose, using ViewModels as the glue between the UI and the data layer. 

***Data*** 
The data layer is built using a **Repository** pattern, with a single source of truth for data. The Repository is responsible for fetching data from the network and caching it in a local database.

The flow of data is being observed using **Kotlin Flows**, which are collected in the **ViewModel** and exposed to the UI as **StateFlows**. This allows for a reactive UI that updates automatically when the data changes.

For example, while loading users there's an loading state emitted, which the UI observes and displays a loading indicator. Once the data is fetched, the loading state is updated to false, and the UI updates to display the list of users.

## Installing the app

This is a repository so the app has to be built from source. To do that, clone the repository and open it in Android Studio, or run this command in the terminal:

```./gradlew assembleDebug``` 

Then, you can run the app on an emulator or a physical device.