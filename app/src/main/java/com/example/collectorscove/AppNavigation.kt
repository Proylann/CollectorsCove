package com.example.collectorscove

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.collectorscove.ui.theme.CoveBackground
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Orders : Screen("orders")
    object Chat : Screen("chat")
    object Notifications : Screen("notifications")
    object Account : Screen("account")
    object SellItem : Screen("sell_item")
    object MyCollection : Screen("my_collection")
    object SignUpCredentials : Screen("signup_credentials")
    object SignUpName : Screen("signup_name")
    object SignUpProfile : Screen("signup_profile")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Shared signup state
    var signupEmail by remember { mutableStateOf("") }
    var signupPassword by remember { mutableStateOf("") }
    var signupFirstName by remember { mutableStateOf("") }
    var signupLastName by remember { mutableStateOf("") }

    val openMenu: () -> Unit = {
        scope.launch { drawerState.open() }
        Unit
    }
    val openNotifications = { navController.navigate(Screen.Notifications.route) }

    AppMenuDrawer(
        drawerState = drawerState,
        onMenuItemClick = { item ->
            scope.launch { drawerState.close() }
            when (item) {
                "My Collection" -> navController.navigate(Screen.MyCollection.route)
                "Sell Item" -> navController.navigate(Screen.SellItem.route)
            }
        },
        onViewProfileClick = {
            scope.launch { drawerState.close() }
            navController.navigate(Screen.Account.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
        onLogoutClick = {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    ) {
        Scaffold(
            containerColor = CoveBackground,
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                if (currentRoute != Screen.Login.route) {
                    BottomNavBar(navController)
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Login.route) {
                        LoginScreen(
                            onSignIn = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            },
                            onGoToSignup = {
                                navController.navigate(Screen.SignUpCredentials.route)
                            }
                        )
                    }

                    composable(Screen.SignUpCredentials.route) {
                        SignUpCredentialsScreen(
                            onNext = { email, password ->
                                signupEmail = email
                                signupPassword = password
                                navController.navigate(Screen.SignUpName.route)
                            }
                        )
                    }

                    composable(Screen.SignUpName.route) {
                        SignUpNameScreen(
                            onNext = { first, middle, last ->
                                signupFirstName = first
                                signupLastName = last
                                navController.navigate(Screen.SignUpProfile.route)
                            }
                        )
                    }

                    composable(Screen.SignUpProfile.route) {
                        SignUpProfileScreen(
                            email = signupEmail,
                            password = signupPassword,
                            firstName = signupFirstName,
                            lastName = signupLastName,
                            onFinish = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.Home.route) {
                        HomeScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }

                    composable(Screen.Explore.route) {
                        ExploreScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }

                    composable(Screen.Orders.route) {
                        OrderScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }

                    composable(Screen.Chat.route) {
                        ChatScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }

                    composable(Screen.Notifications.route) {
                        NotificationScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }

                    composable(Screen.Account.route) {
                        AccountSettingsApp(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications,
                            onLogout = {
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.SellItem.route) {
                        SellItemScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }

                    composable(Screen.MyCollection.route) {
                        MyCollectionScreen(
                            onMenuClick = openMenu,
                            onNotificationsClick = openNotifications
                        )
                    }
                }
            }
        }
    }
}
