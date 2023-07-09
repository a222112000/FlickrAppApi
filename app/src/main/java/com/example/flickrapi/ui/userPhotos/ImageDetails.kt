package com.example.flickrapi.ui.userPhotos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ImageDetails(
    id: String,
    owner: String,
    farm: String,
    server: String,
    secret: String
    ){
    Card(modifier = Modifier.fillMaxWidth()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "User Id:${owner} ",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.em,
                    background = Color.Transparent,
                    fontFamily = FontFamily.Monospace,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(1f, 1f),
                        blurRadius = 1f
                    )
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(7.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 34.sp,

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

            ) {
                AsyncImage(
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        .border(
                            width = 1.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(size = 1.dp)
                        ).size(730.dp)
                        .clip(RoundedCornerShape(topEnd = 1.dp, topStart = 1.dp)),
                    contentScale = ContentScale.Fit,
                    model = "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}.jpg"
                    )
            }
        }
    }
}