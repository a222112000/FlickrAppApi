package com.example.flickrapi.data.people

import kotlinx.serialization.SerialName

data class Userphotos(
    @SerialName("page")
    val page: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("perpage")
    val perpage: Int,
    @SerialName("photo")
    val photo: List<Photo>,
    @SerialName("total")
    val total: Int,
    @SerialName("stat")
    val stat: String
)

fun Userphotos.toUserAllPhotos():UserAllPhotos{
   return UserAllPhotos(page, pages, perpage, photo, stat)
}