package com.example.flickrapi.ui.userPhotos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapi.common.Resource
import com.example.flickrapi.domain.user.UserPhotosUseCase
import com.example.flickrapi.domain.user.getUserPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel @Inject constructor(private val getUserPhotosUseCase: getUserPhotosUseCase,
                                              savedStateHandle: SavedStateHandle,
                                              private val userAllPhotos: UserPhotosUseCase
) :ViewModel(){

    private val _UserPhotos = mutableStateOf(userPhotosState())
    val userPhotos: State<userPhotosState> = _UserPhotos

    private val _UserPhoto = mutableStateOf(userPhotos())
    val userPhoto: State<userPhotos> = _UserPhoto

    init {
        savedStateHandle.get<String>("user_id")?.let {
            getUserPhotos(it)
            getUserAllPhotos(it)
        }
    }

    private fun getUserPhotos(userId: String){
        getUserPhotosUseCase(userId).onEach {
            when(it){
                is Resource.Success ->{

                    _UserPhotos.value = userPhotosState(photos = it.data)
                }
                is Resource.Error ->{
                    _UserPhotos.value = userPhotosState(error = it.message ?: "Something is wrong")
                }is Resource.Loading ->{
                _UserPhotos.value = userPhotosState(isLoading = true)
            }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserAllPhotos(userId: String){
        userAllPhotos(userId).onEach {
            when(it){
                is Resource.Success ->{

                    _UserPhoto.value = userPhotos(photos = it.data)
                }
                is Resource.Error ->{
                    _UserPhoto.value = userPhotos(error = it.message ?: "Something is wrong")
                }is Resource.Loading ->{
                _UserPhoto.value = userPhotos(isLoading = true)
            }
            }
        }.launchIn(viewModelScope)
    }
}