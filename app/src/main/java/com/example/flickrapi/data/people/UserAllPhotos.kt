package com.example.flickrapi.data.people

data class UserAllPhotos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val stat: String
)
