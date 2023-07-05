package com.example.flickrapi.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.flickrapi.ui.Photos.PhotosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen(
    navController: NavController,
    viewModel: PhotosViewModel = hiltViewModel()
) {
    val state = viewModel.Photos.value
    var filteredPhotos by remember { mutableStateOf(state) }
    var isSearching by remember { viewModel.isSearching }

    Surface(color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth(),Arrangement.Center) {
            TopAppBar(
                title = {
                    Text(text = "Flickr APP", modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                        style = TextStyle(color = Color.Black, fontSize = 27.sp),
                        textAlign = TextAlign.Center,
                    )
                })

            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            ) {
                viewModel.searchItems(it)
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 138.dp)) {

                        state.photos?.let {
                            Log.d("PAGEXXX",state.photos?.photos?.page.toString())
                            items(it.photos.photo) {
                                PhotosItem(photo = it,
                                    onUserPhotoClick = { navController.navigate(Screen.UserPhotos.route + "/${it.owner}") }
                                )
                            }
                        }
                    if(state.isLoading){
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(text = "Pagination Loading")

                                CircularProgressIndicator(color = Color.Black)
                            }
                        }
                    }
                }
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
            if(isHintDisplay){
                Text(text = hint, color = Color.LightGray, modifier = Modifier.padding(
                    horizontal = 20.dp, vertical = 12.dp
                ))
            }
        }
    }
