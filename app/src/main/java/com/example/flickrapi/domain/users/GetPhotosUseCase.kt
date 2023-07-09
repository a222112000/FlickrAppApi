package com.example.flickrapi.domain.users

import com.example.flickrapi.common.Resource
import com.example.flickrapi.data.allPhotos.GetPhotos
import com.example.flickrapi.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class getPhotosUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<Resource<GetPhotos>> = flow {
        try {
            emit(Resource.Loading())
            val photos = repository.getPhotos(0)
            emit(Resource.Success(photos))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: " Couldn't reach to network"))
        }
    }
}