
# Development
* Written in 100% Kotlin
* MVVM architecture
* Coroutines and retrofit for networking operations
* Room library for database
* Glide for image processing and display
* Dagger Hilt for dependency injection
* Mockk and jraska library for unit tests
* Google libraries used for map utilities
* sdp and ssp used to maintain layout uniformity
* Timber as alternative to log for logging
* material lib for button

# Setup
* APK file is added to the root layer of folder
* Api calls can be inspected through chucker(for debug builds)

# Structure
* data/ -> data layer where we have repositories and interfaces as well as relevant data classes for network calls
* ext/ -> Extension functions, Helper functions for network calls
* module/ -> Setup of dependency injection modules.
* ui/ -> UI layers where we have activity and fragments as well as view models.
* test/ -> test for view models


