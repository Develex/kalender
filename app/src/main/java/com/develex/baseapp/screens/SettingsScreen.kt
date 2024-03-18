package com.develex.baseapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.R
import com.develex.baseapp.predatastore.DataStoreManager
import com.alorma.compose.settings.ui.SettingsSwitch
import com.develex.baseapp.predatastore.Settings
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, vm: MainViewModel) {

    val dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()
    val themeUserSetting = vm.themeUserSetting.collectAsState()
    val themeAutoUserSetting = vm.themeAutoUserSetting.collectAsState()

    val darkModeState = rememberBooleanSettingState(themeUserSetting.value)
    val autoModeState = rememberBooleanSettingState(themeAutoUserSetting.value)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {
                Text(
                    text = "Instellingen",
                    style = typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(top=20.dp)
                )
            SettingsSwitch(
                icon = {
                    if (themeAutoUserSetting.value) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.night_sight_auto_24px),
                            contentDescription = "Follow System"
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.night_sight_auto_off_24px),
                            contentDescription = "Follow System"
                        )
                    }

                },
                title = { Text(text = "Systeem voorkeuren") },
                subtitle = { Text(text = "Gebruik de systeem voorkeuren") },
                onCheckedChange = { newValue ->
                    run {
//                        set correct values for auto mode and switch manual dark mode aff
                        vm.setThemeAutoUserSetting(newValue)
                        darkModeState.reset()
                        coroutineScope.launch {
                            dataStoreManager.saveToDataStore(Settings(autoMode = newValue))
                        }
                    }
                },
                state = autoModeState,
            )
            SettingsSwitch(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.dark_mode_24px),
                        contentDescription = "Dark Mode"
                    )
                },
                title = { Text(text = "Dark Modus") },
                subtitle = { Text(text = "Gebruik Dark Modus") },
                onCheckedChange = { newValue ->
                    run {
                        vm.setThemeUserSetting(newValue)
                        coroutineScope.launch {
                            dataStoreManager.saveToDataStore(Settings(darkMode = newValue))
                        }
                    }
                },
                state = darkModeState,
                enabled = !themeAutoUserSetting.value,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleSettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController(), vm = MainViewModel())
}