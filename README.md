# POTD

This app lets user search the NASA picture of the day by selecting the date. User can mark an image as favorite by clicking the heart Icon.
Further user can view the favorites on the favorites Screen.


## ScreenShots

| Description        | Search Screen           | Favorites Screen  |
| ------------- |:-------------:| :-----:|
|     Mock up  | <img width="447" alt="Screenshot 2022-11-13 at 10 19 50 PM" src="https://user-images.githubusercontent.com/5801370/201533800-d09148ac-aa2c-496d-a795-33dc205b7e80.png"> | <img width="443" alt="Screenshot 2022-11-13 at 10 14 46 PM" src="https://user-images.githubusercontent.com/5801370/201533835-4109c0a1-02ab-49c8-8ea8-38d7ce906845.png"> |



## Support Version
minSdkVersion = 21
targetSdkVersion = 33



## Features

- Search Image/ Video by Date
- Mark/Unmark as favorite
- View Favorites
- Offline Support
- Dark mode support
- Portrait/Landscape Orientation Support

(Unit tests are also added for the Repository)




## Permissions

- Internet Connection.
- Network State

## Tech Stack

  * [100% Kotlin](https://kotlinlang.org/)
    + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    + [Kotlin Flow](https://kotlinlang.org/docs/flow.html) - data flow across all app layers, including views
  * [Retrofit](https://square.github.io/retrofit/) - networking
  * [Jetpack](https://developer.android.com/jetpack)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when
      lifecycle state changes
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related
      data in a lifecycle-aware way
    * [Room](https://developer.android.com/jetpack/androidx/releases/room) - store offline cache
  * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - dependency injection (dependency retrieval)
  * [Glide](https://github.com/bumptech/glide) - animation library
* Modern Architecture
  * [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
  * Single activity architecture
  * MVVM + MVI (presentation layer)
  * [Android Architecture components](https://developer.android.com/topic/libraries/architecture)
    ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    , [Kotlin Flow](https://kotlinlang.org/docs/flow.html))
  * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin extensions
* UI
  * Reactive UI
  * [View Binding](https://developer.android.com/topic/libraries/view-binding) - retrieve xml view ids
    (used for [NavHostActivity](app/src/main/java/com/igorwojda/showcase/app/presentation/NavHostActivity.kt) only)

* Testing
  * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit 4](https://junit.org/junit4/))
  
* Gradle
  * [Gradle Plugins](https://plugins.gradle.org/)
    * [Android Gradle](https://developer.android.com/studio/releases/gradle-plugin) - standard Android Plugins
    

## Architecture

By dividing a problem into smaller and easier to solve sub-problems, we can reduce the complexity of designing and
maintaining a large system. Each module is independent build-block serving a clear purpose. We can think about each
feature as the reusable component, equivalent of [microservice](https://en.wikipedia.org/wiki/Microservices) or private
library.

The modularized code-base approach provides a few benefits:

- reusability - enable code sharing and building multiple apps from the same foundation. Apps should be a sum of their
- features where the features are organized as separate modules.
- [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns) - each module has a clear API.
  Feature-related classes live in different modules and can't be referenced without explicit module dependency. We
  strictly control what is exposed to other parts of your codebase.
- features can be developed in parallel eg. by different teams
- each feature can be developed in isolation, independently from other features
- faster build time.

## Modules

we have three modules in the application

<img width="142" alt="Screenshot 2022-11-13 at 10 53 17 PM" src="https://user-images.githubusercontent.com/5801370/201535613-01a666bd-4951-4005-b641-39f123426584.png">


- `app` -  This is the main module. It contains code that wires multiple modules together (class, dependency injection setup,  etc.) and fundamental application configuration ( required permissions setup, custom Application class, etc.).
- `network`- This module holds the classes that are required to fetch the data from the network. It also creates the dependencies for retrofit.
- `localdb` - This module holds all the classes that are required to save and retrieve the data from the local database. It also creates the dependencies for Room.


## Architecture Pattern

We have followed the MVI architectural pattern which is Unidirectional in Nature. MVI is a simple pattern to refactor the main() function into three parts: Intent (to listen to the user), Model (to process information), and View (to output back to the user).


![w_6vjijlycjkq6rsgxw4kfaypo8](https://user-images.githubusercontent.com/5801370/201535851-570fcc17-60a7-4cad-b078-2fc3eb1c40d4.png)








