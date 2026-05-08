package com.dmilicic.stackoverflowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dmilicic.stackoverflowapp.ui.navigation.Screen
import com.dmilicic.stackoverflowapp.ui.screens.details.DetailsScreen
import com.dmilicic.stackoverflowapp.ui.screens.list.ListScreen
import com.dmilicic.stackoverflowapp.ui.screens.list.ListViewModel
import com.dmilicic.stackoverflowapp.ui.theme.StackOverflowAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StackOverflowAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationStack()
                }
            }
        }
    }
}

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.route) {
        composable(route = Screen.List.route) {
            ListScreen(
                viewModel = viewModel(),
                onClick = { id ->
                    navController.navigate(Screen.Detail.route + "?id=$id")
                }
            )
        }
        composable(
            route = Screen.Detail.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            DetailsScreen(
            )
        }
    }
}