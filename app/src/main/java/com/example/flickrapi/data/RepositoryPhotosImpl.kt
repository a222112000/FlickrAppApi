package com.example.flickrapi.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.data.allPhotos.PaginationResult
import com.example.flickrapi.data.allPhotos.Photo
import com.example.flickrapi.data.allPhotos.Photos
import com.example.flickrapi.data.people.GetUserPhotos

import com.example.flickrapi.domain.Repository
import javax.inject.Inject

class RepositoryPhotosImpl @Inject constructor(private val flickrService: FlickrService) :
    Repository {
    override suspend fun getPhotos(page: Int): GetPhotos {
        return flickrService.getRecentPhotos(page)
    }

    override suspend fun getUserPhotos(userId: String,page: Int): GetUserPhotos {
        return flickrService.getPhotosByUserId(userId,page)
    }
}