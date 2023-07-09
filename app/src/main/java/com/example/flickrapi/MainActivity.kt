package com.example.flickrapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flickrapi.ui.PhotosScreen
import com.example.flickrapi.ui.Screen
import com.example.flickrapi.ui.UserPhotosScreen
import com.example.flickrapi.ui.theme.FlickrApiTheme
import com.example.flickrapi.ui.userPhotos.ImageDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screen.UsersPhotos.route ){
                        composable(route = Screen.UsersPhotos.route){
                            PhotosScreen(navController = navController)
                        }
                        composable(route = Screen.UserPhotos.route+"/{user_id}"){
                            UserPhotosScreen(navController)
                        }

                        composable(route = Screen.PhotoDetails.route+"/{id}/{owner}/{farm}/{server}/{secret}",
                        ) {
                            it.arguments?.let {
                                ImageDetails(
                                    it.getString("id")!!,
                                    it.getString("owner")!!,
                                    it.getString("farm")!!,
                                    it.getString("server")!!,
                                    it.getString("secret")!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
