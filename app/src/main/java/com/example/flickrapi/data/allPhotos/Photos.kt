package com.example.flickrapi.data.allPhotos

import com.google.gson.annotations.SerializedName

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    @SerializedName("photo")
    var photo: List<Photo>,
    val total: Int
)