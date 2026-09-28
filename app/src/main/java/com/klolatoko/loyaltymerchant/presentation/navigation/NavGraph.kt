package com.klolatoko.loyaltymerchant.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.klolatoko.loyaltymerchant.presentation.auth.ForgotPasswordScreen
import com.klolatoko.loyaltymerchant.presentation.auth.SignInScreen
import com.klolatoko.loyaltymerchant.presentation.auth.SignUpScreen
import com.klolatoko.loyaltymerchant.presentation.dashboard.DashboardViewModel
import com.klolatoko.loyaltymerchant.presentation.dashboard.MainDashboardScreen
import com.klolatoko.loyaltymerchant.presentation.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up")
    object ForgotPassword : Screen("forgot_password")
    object Main : Screen("main")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                onSignIn = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUp = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onResetPassword = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.ForgotPassword.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Main.route) {
            val dashboardViewModel: DashboardViewModel = viewModel()
            MainDashboardScreen(viewModel = dashboardViewModel)
        }
    }
}
