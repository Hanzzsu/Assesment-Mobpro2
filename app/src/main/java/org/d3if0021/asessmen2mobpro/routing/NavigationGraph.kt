package org.d3if3062.mobpro1.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3062.mobpro1.database.laundryDb
import org.d3if3062.mobpro1.ui.screen.DetailScreen
import org.d3if3062.mobpro1.ui.screen.MainScreen
import org.d3if3062.mobpro1.ui.screen.MainViewModel
import org.d3if3062.mobpro1.util.ViewModelFactory

@Composable
fun NavigationGraph(navControl: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val db = laundryDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)

    NavHost(
        navController = navControl,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            MainScreen(navControl, viewModel)
        }

        composable(route = Screen.AddData.route) {
            DetailScreen(navControl, viewModel)
        }

        composable(route = Screen.DetailScreen.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let {
                DetailScreen(navControl, viewModel, it)
            } ?: run {
                DetailScreen(navControl, viewModel)
            }
        }
    }

}