## ABOUT 

This is a sample app to demonstrate the standardized architecture used across Android apps.

UI is built using Jetpack Compose, using ViewModels as the glue between the UI and the data layer. The data layer is built using a Repository pattern, with a single source of truth for data. The Repository is responsible for fetching data from the network and caching it in a local database.

The flow of data is being observed using Kotlin Flows, which are collected in the ViewModel and exposed to the UI as StateFlows. This allows for a reactive UI that updates automatically when the data changes.