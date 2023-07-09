package com.example.flickrapi.domain.user

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.flickrapi.common.Resource
import com.example.flickrapi.data.people.GetUserPhotos
import com.example.flickrapi.data.people.Photo
import com.example.flickrapi.domain.Repository
import com.example.flickrapi.domain.users.PhotosDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class getUserPhotosUseCase @Inject constructor(private val repositoryPhotos: Repository) {

    operator fun invoke(userId: String): Flow<Resource<Pager<Int, Photo>>> = flow {
        try {
            emit(Resource.Loading<Pager<Int, Photo>>())
            val photos = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    UserDataSource(repositoryPhotos,userId)
                }
            )
            emit(Resource.Success<Pager<Int, Photo>>(photos))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: " Couldn't reach to network"))
        }
    }
}