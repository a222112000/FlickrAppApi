package com.example.flickrapi.ui.userPhotos

import com.example.flickrapi.data.people.GetUserPhotos

data class userPhotosState(
    val isLoading: Boolean = false,
    val photos: GetUserPhotos? = null,
    val error: String = ""
)