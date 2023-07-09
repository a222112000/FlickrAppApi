package com.example.flickrapi.ui.Photos

import androidx.paging.Pager
import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.allPhotos.Photo

data class GetPhotosState(
    val isLoading: Boolean = false,
    var photos: List<Photo>? = null,
    val error: String = ""
)