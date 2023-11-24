package com.example.fishingnotes.util

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }


    NavigationBar {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    if (currentRoute != item.route) {
                        selectedItemIndex = index

                        val isInBackStack =
                            navController.popBackStack(item.route, inclusive = false)

                        if (!isInBackStack) {
                            navController.navigate(item.route)
                        }
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}