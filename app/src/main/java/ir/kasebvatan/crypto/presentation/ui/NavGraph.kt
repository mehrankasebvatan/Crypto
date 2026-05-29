package ir.kasebvatan.crypto.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.kasebvatan.crypto.presentation.ui.screen.CoinDetailScreen
import ir.kasebvatan.crypto.presentation.ui.screen.CoinListScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.CoinList.route
    ){
        composable(route = Screen.CoinList.route) {
            CoinListScreen(
                onCoinClick = { coinId ->
                    navController.navigate(Screen.CoinDetail.createRoute(coinId))
                }
            )
        }
        composable(
            route = Screen.CoinDetail.route,
            arguments = listOf(
                navArgument("coinId") { type = NavType.StringType }
            )
        ) {
            CoinDetailScreen()
        }
    }
}
