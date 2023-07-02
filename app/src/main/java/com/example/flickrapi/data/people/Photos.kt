package com.example.flickrapi.data.people

import kotlinx.serialization.SerialName

data class Photos(
    @SerialName("page")
    val page: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("perpage")
    val perpage: Int,
    @SerialName("photo")
    val photo: List<Photo>,
    @SerialName("total")
    val total: Int
)