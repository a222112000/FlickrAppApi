package com.example.flickrapi.domain

import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.people.GetUserPhotos

interface Repository {
    suspend fun getPhotos(page: Int): GetPhotos
    suspend fun getUserPhotos(userId: String,page: Int): GetUserPhotos
}