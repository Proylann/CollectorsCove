package com.example.collectorscove

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Orders : Screen("orders")
    object Chat : Screen("chat")
    object Notifications : Screen("notifications")
    object Account : Screen("account")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppMenuDrawer(drawerState = drawerState) {
        Scaffold(
            bottomBar = {
                BottomNavBar(navController)
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onNotificationsClick = { navController.navigate(Screen.Notifications.route) }
                        )
                    }

                    composable(Screen.Explore.route) {
                        ExploreScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onNotificationsClick = { navController.navigate(Screen.Notifications.route) }
                        )
                    }

                    composable(Screen.Orders.route) {
                        OrderScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            onNotificationsClick = { navController.navigate(Screen.Notifications.route) }
                        )
                    }

                    composable(Screen.Chat.route) {
                        ChatScreen()
                    }

                    composable(Screen.Notifications.route) {
                        NotificationScreen()
                    }

                    composable(Screen.Account.route) {
                        AccountSettingsApp()
                    }
                }
            }
        }
    }
}
