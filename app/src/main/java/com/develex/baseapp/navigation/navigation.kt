package com.develex.baseapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route : String) {
    object Page2 : Screens("Page2_route")
    object Home : Screens("home_route")
    object Settings : Screens("setting_route")

}

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route:  String = ""
) {
    fun bottomNavigationItems() :List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Page2",
                icon = Icons.Filled.Home,
                route = Screens.Page2.route
            ),
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                route = Screens.Settings.route
            ),

        )
    }
}