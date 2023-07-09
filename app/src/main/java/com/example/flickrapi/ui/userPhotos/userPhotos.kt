package com.example.flickrapi.ui.userPhotos

import androidx.paging.Pager
import com.example.flickrapi.data.people.GetUserPhotos
import com.example.flickrapi.data.people.Photo

data class userPhotos(
    val isLoading: Boolean = false,
    val photos: GetUserPhotos? = null,
    val error: String = ""
)