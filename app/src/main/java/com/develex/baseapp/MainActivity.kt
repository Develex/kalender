package com.develex.baseapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.develex.baseapp.data.AppointmentDatabase
import com.develex.baseapp.navigation.BottomNavigationBar
import com.develex.baseapp.predatastore.DataStoreManager
import com.develex.baseapp.screens.AppointmentViewModel
import com.example.compose.AppTheme
import kotlinx.coroutines.launch

// Dataclass for keeping track if the app is in darkmode
data class DarkTheme(val isDark: Boolean = false)

val LocalTheme = compositionLocalOf { DarkTheme() }

class MainActivity : ComponentActivity() {


    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            AppointmentDatabase::class.java,
            "appointment_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private val appointmentViewModel by viewModels<AppointmentViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppointmentViewModel(db.appointmentDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val vm: MainViewModel = viewModel()

            val dataStoreManager: DataStoreManager = DataStoreManager(this)
            LaunchedEffect(true) {
                lifecycleScope.launch {
                    val settings = dataStoreManager.getFromDataStore()
                    settings.collect() { c ->
                        vm.setThemeUserSetting(c.darkMode)
                        vm.setThemeAutoUserSetting(c.autoMode)
                    }
                }
            }


            val themeUserSetting by vm.themeUserSetting.collectAsState()
            val themeAutoUserSettings by vm.themeAutoUserSetting.collectAsState()
            val darkTheme = if (themeAutoUserSettings) {
                DarkTheme(isSystemInDarkTheme())
            } else {
                DarkTheme(themeUserSetting)
            }

            CompositionLocalProvider(LocalTheme provides darkTheme) {
                AppTheme(darkTheme = LocalTheme.current.isDark) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        BottomNavigationBar(vm, appointmentViewModel, applicationContext)
                    }
                }
            }
        }
    }
}