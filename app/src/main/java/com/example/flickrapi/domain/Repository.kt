package com.example.flickrapi.domain

import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.people.GetUserPhotos

interface Repository {
    suspend fun getPhotos(): GetPhotos
    suspend fun getUserPhotos(userId: String): GetUserPhotos
}