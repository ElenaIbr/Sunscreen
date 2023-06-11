package com.example.sunscreen.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.sunscreen.R

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Profile,
        BottomNavItem.UvIndex,
        BottomNavItem.Notifications
    )
    val currentDestination = remember {
        mutableStateOf(navController.currentDestination?.route)
    }

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.primary_text_color),
                    selectedTextColor = colorResource(id = R.color.primary_text_color),
                    unselectedIconColor = colorResource(id = R.color.disable),
                    unselectedTextColor = colorResource(id = R.color.disable),
                    indicatorColor = Color.White
                ),
                selected = item.screenRoute ==  currentDestination.value,
                onClick = {
                    navController.navigate(item.screenRoute)
                },
                label = {
                    Text(
                        text = item.title
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "${item.title} Icon",
                    )
                }
            )
        }
    }
}
