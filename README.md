# Junior_task_users
========================
Task:
---------------------
Make an app displaying a list of users.
Each user is clickable and user's window displays user info and his friends (also clickable).
Loaded data is cached and not requested again when restarting an app.
User list can be refreshed using cache data.
API: [API]("https://firebasestorage.googleapis.com/v0/b/candidates--questionnaire.appspot.com/o/users.json?alt=media&token=e3672c23-b1a5-4ca7-bb77-b6580d75810c")

Screens:
-----------------------
- User list screen
  * presented as a list
  * each item of the list includes name, email and isActive state
  * user details are available only for active users (isActive == true)
- User details screen
  * includes text fields name, age, company, email, phone, address and about
  * pressing an email field opens an app for sending email and pastes user email in the addressee field
  * pressing a phone field opens an app for calls and pastes user phone in the phone number field
  * eyeColor is presented as a dot of specified color (brown, green, blue)
  * favoriteFruit is presented as a specified icon (apple, banana, strawberry)
  * registered field has "HH:mm dd.MM.yy" format
  * latitude and longitude fields are displayed in a row and open a maps app with specified coordinates
  * friends list is similar to "User list"
  * back button pressed provides transitions through the entire hierarchy
  
  
  Learned and worked with:
  ------------------------
  - Dependecy Injection (Hilt)
    * @Inject, @AndroidEntryPoint, @HiltViewModel, @Module, @Provides annotations
  - Kotlin coroutines
  - Navigation graph
  - DataBinding (TODO)
  - Clean architecture (TODO)
  - MVVM pattern (TODO)
  - Implicit intents
  
  Screenshots
  -----------------------
