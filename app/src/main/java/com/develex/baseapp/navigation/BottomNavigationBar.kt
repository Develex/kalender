package com.develex.baseapp.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Database
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.R
import com.develex.baseapp.data.Appointment
import com.develex.baseapp.data.AppointmentDatabase
import com.develex.baseapp.screens.AppointmentViewModel
import com.develex.baseapp.screens.HomeScreen
import com.develex.baseapp.screens.AppointmentsScreen
import com.develex.baseapp.screens.SettingsScreen

// important to pass the instance of the ViewModel to the next composable. otherwise it creates a new instance. :(
@SuppressLint("AutoboxingStateCreation")
@Composable
fun BottomNavigationBar(
    vm: MainViewModel,
    appointmentViewModel: AppointmentViewModel,
    context: Context
) {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
//                                Resetting currentAppointment. this is only needed here if the user uses the navBar to exit the AppointmentScreen
                                vm.setCurrentAppointment(0)
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.Appointments.route) {
                AppointmentsScreen(
                    navController,
                    vm,
                    appointmentViewModel
                )
            }
            composable(Screens.Home.route) {
                HomeScreen(navController, vm, appointmentViewModel)
            }
            composable(Screens.Settings.route) {
                SettingsScreen(navController, vm)
            }
        }
    }
}