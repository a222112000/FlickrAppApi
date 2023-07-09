package com.example.flickrapi.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.flickrapi.data.allPhotos.Photo
import com.example.flickrapi.ui.Photos.PhotosViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen(
    navController: NavController,
    viewModel: PhotosViewModel = hiltViewModel()
) {
    val state = viewModel.Photos.value.photos?.flow?.collectAsLazyPagingItems()
    val search = viewModel.getPhotos.value
    var filteredPhotos by remember { mutableStateOf(state) }
    var isSearching by remember { viewModel.isSearching }
    var isLoading by remember {
        viewModel.isLoading
    }

    Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "Flickr APP",
                                modifier =Modifier.padding(top = 13.dp, end = 8.dp),
                                style = TextStyle(color = Color.Black, fontSize = 27.sp),
                                textAlign = TextAlign.Start,
                            )
                            SearchBar(
                                hint = "Search...",

                                ) {
                                viewModel.searchItems(it)
                            }
                        }
                    })
    }, modifier = Modifier.fillMaxWidth()) {

        Surface(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 48.dp), Arrangement.Top) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content = {
                            if (isSearching) {
                                search.photos?.let {
                                    items(it) {
                                        PhotosItem(photo = it,
                                            onUserPhotoClick = { navController.navigate(Screen.UserPhotos.route + "/${it.owner}") }
                                        )
                                    }
                                }
                            } else if(!isLoading) {
                                state?.itemCount?.let {
                                    items(it) { index ->
                                        state.get(index)?.let {
                                            PhotosItem(photo = it,
                                                onUserPhotoClick = { navController.navigate(Screen.UserPhotos.route + "/${it.owner}") }
                                            )
                                        }
                                    }
                                }
                            }
                        }, contentPadding = PaddingValues(16.dp)
                    )
                    if (viewModel.Photos.value.error.isNotBlank()) {
                        Text(
                            text = viewModel.Photos.value.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                        )
                    }
                    if (viewModel.Photos.value.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }

        }
    }
}

    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier,
        hint: String = "",
        onSearch: (String) -> Unit = {}
    ){
        var text  by remember {
            mutableStateOf("")
        }
        var isHintDisplay by remember {
            mutableStateOf(hint != "")
        }
        Box(modifier = modifier){
            BasicTextField(value = text, onValueChange ={
                text = it
                onSearch(it)
            },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, CircleShape)
                    .background(Color.Black, CircleShape)
                    .padding(horizontal = 0.dp, vertical = 0.dp)
                    .onFocusChanged {
                        val focus = it
                        isHintDisplay = focus.isFocused && text.isNotEmpty()
                    },
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            // margin left and right
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(size = 1.dp)
                            )
                            .padding(all = 16.dp), // inner padding
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(text.isEmpty()){
                            Text(text = "Search...", style = TextStyle(color = Color.Gray, fontSize = 18.sp))
                        }
                        innerTextField()
                        Spacer(modifier = Modifier.width(width = 1.dp))
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search...",
                            tint = Color.Gray
                        )

                    }
                }
            )

        }
    }
