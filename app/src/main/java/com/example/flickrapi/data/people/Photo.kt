package com.example.flickrapi.data.people


import kotlinx.serialization.SerialName

data class Photo(
    @SerialName("farm")
    val farm: Int,
    @SerialName("id")
    val id: String,
    @SerialName("isfamily")
    val isfamily: Int,
    @SerialName("isfriend")
    val isfriend: Int,
    @SerialName("ispublic")
    val ispublic: Int,
    @SerialName("owner")
    val owner: String,
    @SerialName("secret")
    val secret: String,
    @SerialName("server")
    val server: String,
    @SerialName("title")
    val title: String
)