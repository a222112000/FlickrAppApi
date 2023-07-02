package com.example.flickrapi.data.allPhotos

import com.google.gson.annotations.SerializedName

data class GetPhotos(
    @SerializedName("photos")
    val photos: Photos
)
