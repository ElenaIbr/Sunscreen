package com.example.sunscreen.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

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
                    selectedIconColor = UiColors.textContent.primary,
                    selectedTextColor = UiColors.textContent.primary,
                    unselectedIconColor = UiColors.textContent.disabled,
                    unselectedTextColor = UiColors.textContent.disabled,
                    indicatorColor = UiColors.background.baseWhite
                ),
                selected = item.screenRoute ==  currentDestination.value,
                onClick = {
                    navController.navigate(item.screenRoute)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "${item.title} ${stringResource(id = R.string.icon).uppercase()}",
                    )
                }
            )
        }
    }
}
