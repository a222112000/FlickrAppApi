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

    private var cachePhotos = PhotosState()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    fun searchItems(query: String) {
        var items = _AllPhotos.value
        val listSearch = if(isSearchStarting){
            items
        }else{
            cachePhotos
        }
        // Replace with your API call to fetch items
        getPhotosUseCase().onEach {
            if (query.isEmpty()) {
                items = cachePhotos
                isSearching.value = false
                isSearchStarting = true
                return@onEach
            }
            val res =  listSearch.photos?.photos?.photo?.filter { it.title.contains(query.trim(),
                ignoreCase = true) || it.owner.contains(query.trim()) }

            if (res != null) {
                _AllPhotos.value.photos?.photos?.photo = res
            }
            isSearching.value = true

            when(it){
                is Resource.Success -> {
                    if (isSearchStarting) {
                        cachePhotos = _AllPhotos.value
                        isSearchStarting = false
                    }
                     var result = it.data
                         //result = res
                        _AllPhotos.value = PhotosState(photos = result)
                    Log.d("UYUY",_AllPhotos.value.photos.toString())

                }
                is Resource.Error -> {
                    _AllPhotos.value = PhotosState(error = it.message ?: "Something is wrong")
                }is Resource.Loading -> {
                _AllPhotos.value = PhotosState(isLoading = true)
            }
            }
        }.launchIn(viewModelScope)

    }

    var Photos: State<PhotosState> = _AllPhotos

    init {
        getPhotos()
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