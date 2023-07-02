package com.example.flickrapi.data.allPhotos

data class Photo(
    val id: String,
    val isfamily: Int,
    val farm:Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)