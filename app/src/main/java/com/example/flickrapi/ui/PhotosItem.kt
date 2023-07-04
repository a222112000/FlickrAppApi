package com.example.flickrapi.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.flickrapi.data.allPhotos.Photo

@Composable
fun PhotosItem(
    photo: Photo,
    onUserPhotoClick: (Photo) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUserPhotoClick(photo) }
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "User Id:${photo.owner} ",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 24.sp,

                    fontWeight = FontWeight.W800,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.em,
                    background = Color.Transparent,
                    fontFamily = FontFamily.Monospace,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(5f, 5f),
                        blurRadius = 5f
                    )
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${photo.title} ",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 20.sp,

                        fontWeight = FontWeight.W800,
                        fontStyle = FontStyle.Italic,
                        background = Color.Transparent,
                        fontFamily = FontFamily.Serif,

                        ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Box {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(start = 1.dp, end = 1.dp, bottom = 1.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    AsyncImage(
                        contentDescription = photo.title,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .size(128.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit,
                        model =
                        "https://farm${photo.farm}.staticflickr.com/${photo.server}/buddyicons/${photo.owner}.jpg",

                        )
//
                }
            }
        }

    }
}