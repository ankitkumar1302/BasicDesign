package com.viprab.basicdesign.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.ui.detail.DetailScreen
import com.viprab.basicdesign.ui.favorites.FavoritesScreen
import com.viprab.basicdesign.ui.genres.GenresScreen
import com.viprab.basicdesign.ui.home.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        // Home screen route
        composable(
            route = "home",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            HomeScreen(
                onBookClick = { book ->
                    navController.navigate("detail/${book.id}")
                }
            )
        }

        // Genres screen route
        composable(
            route = "genres",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            GenresScreen(navController = navController)
        }

        // Favorites screen route
        composable(
            route = "favorites",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            FavoritesScreen(navController = navController)
        }

        // Detail screen route
        composable(
            route = "detail/{bookId}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
            bookId?.let {
                DetailScreen(navController, it)
            }
        }
    }
}

//@Composable
//fun GenresScreen(navController: NavController) {
//    Text("Genres Screen")
//}
//
//@Composable
//fun FavoritesScreen(navController: NavController) {
//    Text("Favorites Screen")
//}