package com.sw.placeholder.pokemonsampleapp.ui.navigation

import ListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sw.placeholder.pokemon.detail.DetailScreen
import com.sw.placeholder.pokemon.fav.FavScreen

@Composable
fun MainNavigation(navHostController: NavHostController = rememberNavController()) {

    NavHost(navController = navHostController, startDestination = "list") {
        composable(route = "list") {
           ListScreen(
                onClick = { name ->
                    navHostController.navigate("detail/$name")
                }
            )
        }
        composable("detail/{name}",
            listOf(navArgument("name") { type = NavType.StringType })) {
         DetailScreen(
                onClick = {
                    navHostController.popBackStack()
                },
             onClickFav = {
                    navHostController.navigate("fav")
                }
            )
        }
        composable(route = "fav") {
            FavScreen(
                onClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }

}


