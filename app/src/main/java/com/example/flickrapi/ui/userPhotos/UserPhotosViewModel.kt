package com.example.flickrapi.ui.userPhotos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapi.common.Resource
import com.example.flickrapi.domain.getUserPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel @Inject constructor(private val getUserPhotosUseCase: getUserPhotosUseCase,
                                              savedStateHandle: SavedStateHandle) :ViewModel(){

    private val _UserPhotos = mutableStateOf(userPhotosState())
    val userPhotos: State<userPhotosState> = _UserPhotos

    init {
        savedStateHandle.get<String>("user_id")?.let {
            getUserPhotos(it)
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
}