package com.example.flickrapi.ui.Photos

import com.example.flickrapi.data.allPhotos.GetPhotos

data class PhotosState(
    val isLoading: Boolean = false,
    val photos: GetPhotos? = null,
    val error: String = ""
)