package com.example.flickrapi.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.flickrapi.ui.userPhotos.UserPhotosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserPhotosScreen(viewModel: UserPhotosViewModel = hiltViewModel()) {

    val state = viewModel.userPhotos.value
    state.photos?.photos?.let { photos ->
    Scaffold(modifier = Modifier.fillMaxWidth(),

    bottomBar = {
        MaterialTheme(

            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
        ) {
            BottomAppBar(
                content = {
                    LazyColumn {

                        item {

                            Text(modifier = Modifier.fillMaxWidth().align(CenterVertically), textAlign = TextAlign.Center,text = "User ${photos.photo.get(0).owner}\nPages are: ${photos.pages} \n All Photos are:${photos.total}",

                                )
                        }
                    }
                })
        }
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 228.dp)) {
                    items(photos.photo) { photo ->
                        Box {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.BottomCenter)
                                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                                border = BorderStroke(2.dp, Color.Black),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                AsyncImage(
                                    contentDescription = photo.title,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentScale = ContentScale.FillWidth,
                                    model =
                                    "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",

                                    )
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

                )
            }
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            }

        }
    }
}

