package com.example.flickrapi.data.allPhotos

data class PaginationResult<T>(
    val data: List<T>,
    val nextPage: Int?
)
