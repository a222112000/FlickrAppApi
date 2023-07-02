package com.example.flickrapi.ui

sealed class Screen(val route: String){
    object UsersPhotos: Screen("users_photos")
    object UserPhotos: Screen("user_photos")
}
