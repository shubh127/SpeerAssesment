Speer Assignment

Language Used: Kotlin

Architectural Pattern: MVVM + Repository Pattern Feature Coverage:

Features Covered

Search github user
User Detail page (name, username, bio, email, location, followerCount, followingCount, repoCount)
Follower and Following listing (clickable)

Package Structure:

After opening the application package, we see the following structure:

• ‘data’ folder: Responsible for providing data to the UI Layer. Data is being fetched from  Github APIs. The data layer is reactive. It uses LiveData to hold its state, to which the UI layer listens via ViewModel and updates itself accordingly.

• ‘helper’ folder: contains some helper classes and constants.

• ‘listener’ folder: contains interfaces acting as listners of list item clicks.

• ‘ui’ folder: The UI layer of the application. Contains Activity, Fragment, Adapters and ViewHolders etc.

• ‘viewmodel’ folder: The ViewModel interacts with the repository implementation of data layer to fetch the data.

• ‘MyApplication’ file: Application file.

TechStack Used:

• Android Jetpack Navigation Component: To navigate between fragments.

• ViewModels: To fetch data from repository layer and provide it to ui layer.

• LiveData: To keep persistent and upto date data.

• Glide: To load and cache images from URL.

• Koin: To implement dependency injection.

• DataBinding: To bind data directly with xml views

• Retrofit: For API calling

• Pagging: To get data from API in chunks and maintain state of the list
