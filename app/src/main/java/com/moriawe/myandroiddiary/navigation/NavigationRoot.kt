package com.moriawe.myandroiddiary.navigation

import MainViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.asFlow
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moriawe.myandroiddiary.presentation.daily_stats.DailyStatsScreenRoot
import com.moriawe.myandroiddiary.presentation.daily_stats.DailyStatsViewModel
import com.moriawe.myandroiddiary.presentation.login.LoginScreenRoot
import com.moriawe.myandroiddiary.presentation.login.LoginViewModel

@Composable
fun NavigationRoot(
    navController: NavHostController,
    innerPadding: PaddingValues,
    mainViewModel: MainViewModel,
    onAuthentication: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = Route.LoginRoute.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        //authGraph(navController)
        //diaryGraph(navController)
        composable(route = Route.LoginRoute.route) {
            LoginScreenRoot(
                viewModel = LoginViewModel(),
                onLogin = {
                    onAuthentication()
                }
            )
        }
        composable(route = Route.DailyStatsRoute.route) {
            DailyStatsScreenRoot(
                viewModel = DailyStatsViewModel(),
            )
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = Route.LoginRoute.route,
        route = "AuthGraph"
    ) {
        composable(route = Route.LoginRoute.route) {
            LoginScreenRoot(
                viewModel = LoginViewModel(),
                onLogin = {
                    navController.navigate(Route.DailyStatsRoute.route)
                }
            )
        }
    }
}

private fun NavGraphBuilder.diaryGraph(navController: NavHostController) {
    navigation(
        startDestination = Route.DailyStatsRoute.route,
        route = "DiaryGraph"
    ) {
        composable(route = Route.DailyStatsRoute.route) {
            DailyStatsScreenRoot(
                viewModel = DailyStatsViewModel(),
            )
        }
    }
}

/*
        composable(route = Screen.TimerScreen.route) {
            val viewModel = hiltViewModel<TimerViewModel>()
            val state by viewModel.timerState.collectAsState()
            val onEvent = viewModel::onEvent
            TimerScreen(
                state = state,
                modifier = modifier,
                onEvent = onEvent,
                onOpenDialog = {
                navController.navigate(Screen.DialogScreen.withArgs(it))
            })
        }
 */