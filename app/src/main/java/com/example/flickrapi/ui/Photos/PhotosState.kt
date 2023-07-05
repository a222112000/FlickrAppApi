package com.example.flickrapi.ui.Photos

import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.allPhotos.Photo

data class PhotosState(
    val isLoading: Boolean = false,
    var photos: GetPhotos? = null,
    val error: String = ""
)