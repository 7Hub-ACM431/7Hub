package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.Views.ChatScreen.ChatScreen
import com.example.myapplication.Views.CourseView.CoursesScreen
import com.example.myapplication.Views.GroupsView.GroupsScreen
import com.example.myapplication.Views.HomeView
import com.example.myapplication.Views.LoginView.AuthState
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.Views.LoginView.LoginViewModel
import com.example.myapplication.Views.ResetPassword.ResetPasswordScreen
import com.example.myapplication.Views.ResetPassword.ResetPasswordViewModel
import com.example.myapplication.Views.ChatList.ChatListScreen
import com.example.myapplication.Views.ReviewScreen.ReviewScreen
import com.example.myapplication.Views.CourseView.CourseDetailScreen
import com.example.myapplication.Views.CourseView.CourseDetailViewModel
import com.example.myapplication.Views.ReviewScreen.ReviewCoursesScreen

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Reviews : BottomNavItem("reviews", Icons.Default.Star, "Reviews")
    object Account : BottomNavItem("account", Icons.Default.Person, "Account")
    object Chat : BottomNavItem("chatlist", Icons.Default.Chat, "Chat")
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Reviews,
        BottomNavItem.Account,
        BottomNavItem.Chat
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = Color(0xFF4285F4)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF4285F4)
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF4285F4),
                    unselectedIconColor = Color(0xFF4285F4),
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@Composable
fun MainScreen(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()
    val authState by loginViewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
            is AuthState.Authenticated -> {
                if (navController.currentDestination?.route == "login") {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        bottomBar = {
            if (authState is AuthState.Authenticated) {
                AppBottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginPage(navController = navController, loginViewModel = loginViewModel)
            }

            composable("resetpw") {
                ResetPasswordScreen(
                    modifier = Modifier,
                    navController = navController,
                    viewModel = ResetPasswordViewModel()
                )
            }

            composable("home") {
                HomeView(navController = navController, loginViewModel = loginViewModel)
            }

            composable("courses") {
                CoursesScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }

            composable(
                "course_detail/{courseCode}",
                arguments = listOf(navArgument("courseCode") { type = NavType.StringType })
            ) { backStackEntry ->
                val courseCode = backStackEntry.arguments?.getString("courseCode") ?: return@composable
                CourseDetailScreen(
                    viewModel = CourseDetailViewModel(),
                    onBackClick = { navController.navigateUp() },
                    onChatClick = { /* Chat işlevi eklenecek */ }
                )
            }

            composable("groups") {
                GroupsScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }

            composable("chatlist") {
                ChatListScreen(
                    onChatClick = { chatId ->
                        navController.navigate("chat/$chatId")
                    }
                )
            }

            composable(
                "chat/{chatId}",
                arguments = listOf(navArgument("chatId") { type = NavType.StringType })
            ) { backStackEntry ->
                val chatId = backStackEntry.arguments?.getString("chatId") ?: return@composable
                ChatScreen(
                    chatId = chatId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("reviews") {
                ReviewScreen(
                    //reviews = emptyList(),
                    onTeacherReviewClick = { navController.navigate("teacherReviews") },
                    onCourseReviewClick = { navController.navigate("courseReviews") }
                )
            }

            composable("account") {
                // Add your Account screen implementation here
                // For now, using a placeholder Text
                Text(
                    text = "Account Screen",
                    modifier = Modifier.padding(16.dp)
                )
            }
            composable("coursesReview") {
                ReviewCoursesScreen(
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}