package com.develex.baseapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.develex.baseapp.R

sealed class Screens(val route: String) {
    object Appointments : Screens("appointments_route")
    object Home : Screens("home_route")
    object Settings : Screens("setting_route")

}

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    @Composable
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Afspraken",
                icon = Icons.Filled.List,
                route = Screens.Appointments.route
            ),
            BottomNavigationItem(
                label = "Kalender",
                icon = ImageVector.vectorResource(id = R.drawable.calendar_month_24px),
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Instellingen",
                icon = Icons.Filled.Settings,
                route = Screens.Settings.route
            ),

            )
    }
}