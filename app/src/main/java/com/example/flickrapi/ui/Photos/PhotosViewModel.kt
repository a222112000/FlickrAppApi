package com.example.flickrapi.ui.Photos

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapi.common.Resource
import com.example.flickrapi.domain.getPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val getPhotosUseCase: getPhotosUseCase) : ViewModel() {

    private var _AllPhotos = mutableStateOf(PhotosState())
    var Photos: State<PhotosState> = _AllPhotos

    init {
        getPhotos()
    }

    fun searchItems(query: String) {
        val items = _AllPhotos.value// Replace with your API call to fetch items
        viewModelScope.launch(Dispatchers.IO) {

            val filtered = if (query.isEmpty()) {
                items.photos?.photos?.photo
            } else {
                items.photos?.photos?.photo?.filter { it.title.contains(query, ignoreCase = true) }

            }
            if (filtered != null) {

                _AllPhotos.value.photos?.photos?.photo = filtered
                Log.d("UYUY",_AllPhotos.value.toString())
            }
        }
    }

    private fun getPhotos(){
        getPhotosUseCase().onEach {
            when(it){
                is Resource.Success -> {
                    _AllPhotos.value = PhotosState(photos = it.data)
                }
                is Resource.Error -> {
                    _AllPhotos.value = PhotosState(error = it.message ?: "Something is wrong")
                }is Resource.Loading -> {
                    _AllPhotos.value = PhotosState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}