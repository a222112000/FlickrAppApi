package com.example.flickrapi.data.people

import com.google.gson.annotations.SerializedName

data class GetUserPhotos(
    @SerializedName("photos")
    val photos: Photos
)
