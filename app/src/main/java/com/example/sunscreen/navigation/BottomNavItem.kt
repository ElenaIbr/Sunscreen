package com.example.sunscreen.navigation

import com.example.sunscreen.R

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var screenRoute: String
) {
    object UvIndex : BottomNavItem("Index", R.drawable.ic_sunny,"uv_index")
    object Profile: BottomNavItem("Profile",R.drawable.ic_person,"profile")
    object Notifications: BottomNavItem("Notifications",R.drawable.ic_notifications,"notifications")
    object Questionnaire: BottomNavItem("Questionnaire",R.drawable.person,"questionnaire")
}
