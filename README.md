# Wintrop Application
#### Application Description

![logo](https://user-images.githubusercontent.com/29167110/72382065-6616dc00-3710-11ea-9958-6819483ac26d.png)


This Application has Two Screens and One Fragment
The First Screen is The Splash Screen To Show The User Application Info With Logo
THe Second Screen is The Main Screen Store All Fragments At One Screen But at this example just one fragment at the main screen

## Application Components
1. Room Database 
2. Kotlin Coroutines
3. Clean Architecture
4. Mvvm Architecture
5. Multi Modular Application
6. Dependencies Management At Gradle
7. State Handler With State Destroyer
8. Application Configuration At WintropApplication
9. Api Requests With Retrofit
10. Reactive Operations With RxJava (At This Example Just Observable)
11. Parcelable Entities
12. Dependency Injection (Motif , Koin)

## Features Description
1. Data Layer: This Layer has The Models for requests , Database, Object Mappers, etc...
At This Layer Should Implement The Data Implementation For THe Application And THis Layer is a sub layer from the Domain Layer

2. Domain Layer: This Layer has the ViewModels To handle the application logic with State Management
Action : This is the Event sent from View To Tell the view Model i want to execute this event by submit Action
State : This is Result About THe Current State in ViewModel 

at this layer always ViewModel Handling each state to view but the View don't know anything about the ViewModel just submit incoming actions
and this actions applied via Observer at the View

3. UI : this layer just show the data or error via State 

## State Description For Example
1. Main State 
    1.1 Empty State
    1.2 Error State
    1.3 Success State

The Empty State is THe Initialization State because each viewModel Should Execute an initial state for view
The Error State : if ViewModel Found any Error then submit an Error State to show user the error details
Success State : Handle The Response from ViewModel to View and Save The State for Rotation or Any View Configuration Changed

## Multi Threading
Each Method At Application Executed at Background Thread and just UI Components executed at Main Thread
Via Kotlin Coroutines

## Application Base Code
This Application Built On Vortex Library To Config the Application
Vortex Link : https://github.com/Vortex-io/Vortex
Bintray Link : https://bintray.com/yt98/Vortex

## Screenshots

![82535731_550057142389978_78404924341223424_n](https://user-images.githubusercontent.com/29167110/72382085-7038da80-3710-11ea-8f0a-ecfdccf05e99.jpg)


![82191080_2437326349931321_2649751945526378496_n](https://user-images.githubusercontent.com/29167110/72382120-8181e700-3710-11ea-8eba-db5eb0c15a31.jpg)


![81976736_1002485890123604_1837372754232344576_n](https://user-images.githubusercontent.com/29167110/72382141-8b0b4f00-3710-11ea-9aaf-d367e6f7abd9.jpg)
