package com.example.flickrapi.ui.userPhotos

import androidx.paging.Pager
import com.example.flickrapi.data.people.GetUserPhotos
import com.example.flickrapi.data.people.Photo

data class userPhotosState(
    val isLoading: Boolean = false,
    val photos: Pager<Int, Photo>? = null,
    val error: String = ""
)