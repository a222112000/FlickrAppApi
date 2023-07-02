package com.example.flickrapi.domain

import android.util.Log
import com.example.flickrapi.common.Resource
import com.example.flickrapi.data.people.GetUserPhotos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class getUserPhotosUseCase @Inject constructor(private val repositoryPhotos: Repository) {

    operator fun invoke(userId: String): Flow<Resource<GetUserPhotos>> = flow {
        try {
            emit(Resource.Loading())
            val photos = repositoryPhotos.getUserPhotos(userId)
            Log.d("PPPP",photos.toString())
            emit(Resource.Success(photos))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: " Couldn't reach to network"))
        }
    }
}