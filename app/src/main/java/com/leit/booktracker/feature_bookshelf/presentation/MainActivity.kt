package com.leit.booktracker.feature_bookshelf.presentation

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
import com.leit.booktracker.feature_bookshelf.presentation.add_edit_book.BookAddEditScreen
import com.leit.booktracker.feature_bookshelf.presentation.book_detail.BookDetailScreen
import com.leit.booktracker.feature_bookshelf.presentation.bookshelf.BookShelfScreen
import com.leit.booktracker.feature_bookshelf.presentation.util.Screen
import com.leit.booktracker.ui.theme.BookTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BookShelfScreen.route
                    ){
                        composable(route = Screen.BookShelfScreen.route){
                            BookShelfScreen(navController = navController)
                        }
                        
                        composable(
                            route = Screen.BookAddEditScreen.route +
                                "?bookId={bookId}",
                            arguments = listOf(
                                navArgument(name = "bookId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            BookAddEditScreen(navController = navController)
                        }
                        
                        composable(
                            route = Screen.BookDetailScreen.route + "?bookId={bookId}",
                            arguments = listOf(
                                navArgument(name = "bookId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            BookDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookTrackerTheme {
        Greeting("Android")
    }
}