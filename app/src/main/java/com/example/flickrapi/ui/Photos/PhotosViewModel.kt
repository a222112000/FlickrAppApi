package com.example.flickrapi.ui.Photos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapi.common.Resource
import com.example.flickrapi.domain.users.PhotosUseCase
import com.example.flickrapi.domain.users.getPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val photosUseCase: PhotosUseCase, private val getPhotosUseCase: getPhotosUseCase) : ViewModel() {

    private var _AllPhotos = mutableStateOf(PhotosState())
    private var _getPhotos = mutableStateOf(GetPhotosState())

    //private var cachePhotos = PhotosState()
    private var getcachePhotos = GetPhotosState()

    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)
    var isLoading = mutableStateOf(false)

    fun searchItems(query: String) {
        var items = _getPhotos.value
        val listSearch = if(isSearchStarting){
            items
        }else{
            getcachePhotos
        }

        // Replace with your API call to fetch items
        getPhotosUseCase().onEach {get->
            if (query.isEmpty()) {
                items = getcachePhotos
                isSearching.value = false
                isSearchStarting = true
                return@onEach
            }
            val res =  listSearch.photos?.filter { photo->
                photo.title.contains(query.trim(),
                ignoreCase = true) || photo.owner.contains(query.trim(),ignoreCase = true) }
            if(isSearchStarting){
                getcachePhotos = _getPhotos.value
                isSearchStarting = false
            }
            if (res != null) {
                _getPhotos.value.photos = res
            }
            isSearching.value = true

            when(get){
                is Resource.Success -> {
                    if (isSearchStarting) {
                        getcachePhotos = _getPhotos.value
                        isSearchStarting = false
                    }
                    get.data?.let {
                        it.photos.photo = _getPhotos.value.photos!!
                    }
                     val result =get.data?.photos?.photo
                    _getPhotos.value = GetPhotosState(photos = result)
                }
                is Resource.Error -> {
                    _getPhotos.value = GetPhotosState(error = get.message ?: "Something is wrong")
                }is Resource.Loading -> {
                _getPhotos.value = GetPhotosState(isLoading = true)
            }
            }
        }.launchIn(viewModelScope)

    }

    var Photos: State<PhotosState> = _AllPhotos
    var getPhotos: State<GetPhotosState> = _getPhotos

    init {
        getPhotos()
        getAllPhotos()
    }

    private fun getPhotos(){
        photosUseCase().onEach {
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

    private fun getAllPhotos(){
        getPhotosUseCase().onEach {
            when(it){
                is Resource.Success -> {
                    isLoading.value = false
                    _getPhotos.value = GetPhotosState(photos = it.data?.photos?.photo)
                }
                is Resource.Error -> {
                    _getPhotos.value = GetPhotosState(error = it.message ?: "Something is wrong")
                }is Resource.Loading -> {
                _getPhotos.value = GetPhotosState(isLoading = true)
                isLoading.value = true
            }
            }
        }.launchIn(viewModelScope)
    }
}