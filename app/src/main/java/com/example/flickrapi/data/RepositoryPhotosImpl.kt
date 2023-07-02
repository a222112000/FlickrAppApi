package com.example.flickrapi.data

import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.people.GetUserPhotos
import com.example.flickrapi.domain.Repository
import javax.inject.Inject

class RepositoryPhotosImpl @Inject constructor(private val flickrService: FlickrService) :
    Repository {
    override suspend fun getPhotos(): GetPhotos {
        return flickrService.getRecentPhotos()
    }

    override suspend fun getUserPhotos(userId: String): GetUserPhotos {
        return flickrService.getPhotosByUserId(userId)
    }
}