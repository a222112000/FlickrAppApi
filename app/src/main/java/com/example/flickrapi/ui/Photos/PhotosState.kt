package com.example.flickrapi.ui.Photos

import androidx.paging.Pager
import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.allPhotos.Photo

data class PhotosState(
    val isLoading: Boolean = false,
    var photos: Pager<Int, Photo>? = null,
    val error: String = ""
)