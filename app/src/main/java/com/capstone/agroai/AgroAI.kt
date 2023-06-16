package com.capstone.agroai

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capstone.agroai.ui.navigation.Screen
import com.capstone.agroai.ui.screen.detail.DetailScreen
import com.capstone.agroai.ui.screen.home.HomeScreen
import com.capstone.agroai.ui.screen.welcome.WelcomeScreen
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Primary900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgroAI(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Primary900,
//        bottomBar = {
//            if(currentRoute != Screen.Detail.route) {
//                BottomBar(navController)
//            }
//        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    navigateTo = { route ->
                        navController.navigate(route)
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("disease") { type = NavType.StringType},
                    navArgument("imageUri") { type = NavType.StringType }
                )
            ) {
                val disease = it.arguments?.getString("disease") ?: ""
                val imageUriString = it.arguments?.getString("imageUri") ?: ""
                DetailScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    disease = disease,
                    imageUriString = imageUriString
                )
            }
        }
    }

}

//@Composable
//private fun BottomBar(
//    navController: NavHostController,
//    modifier: Modifier = Modifier
//) {
//    NavigationBar(
//        modifier = modifier
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        val navigationItems = listOf(
//            NavigationItem(
//                title = stringResource(R.string.home_page),
//                icon = Icons.Default.Home,
//                screen = Screen.Home
//            ),
//            NavigationItem(
//                title = stringResource(R.string.history_page),
//                icon = Icons.Default.List,
//                screen = Screen.History
//            ),
//            NavigationItem(
//                title = stringResource(R.string.profile_page),
//                icon = Icons.Default.Person,
//                screen = Screen.Profile
//            )
//        )
//
//        NavigationBar(
//            containerColor = Primary700,
//        ) {
//            navigationItems.map { item ->
//                NavigationBarItem(
//                    icon = {
//                        Icon(
//                            imageVector = item.icon,
//                            contentDescription = item.title,
//                            tint = Color.White
//                        )
//                    },
//                    label = {
//                        Text(
//                            text = item.title,
//                            fontFamily = Montserrat,
//                            fontWeight = FontWeight.SemiBold,
//                            color = Color.White
//                        )
//                    },
//                    selected = currentRoute == item.screen.route,
//                    onClick = {}
//                )
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewAgroAI() {
    AgroAITheme {
        AgroAI()
    }
}