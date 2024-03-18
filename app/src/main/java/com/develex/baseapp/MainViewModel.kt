package com.develex.baseapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    fun setThemeUserSetting(value: Boolean) {
        _themeUserSetting.value = value
    }

    fun setThemeAutoUserSetting(value: Boolean) {
        _themeAutoUserSetting.value = value
    }

    //    The stateflow for keeping the user setting for dark mode, by default is this automatic (follow system settings)
    private var _themeUserSetting = MutableStateFlow(false)
    val themeUserSetting: StateFlow<Boolean> = _themeUserSetting
    private var _themeAutoUserSetting = MutableStateFlow(false)
    val themeAutoUserSetting: StateFlow<Boolean> = _themeAutoUserSetting
}

