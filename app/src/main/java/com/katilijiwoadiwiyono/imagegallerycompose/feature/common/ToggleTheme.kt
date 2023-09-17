package com.katilijiwoadiwiyono.imagegallerycompose.feature.common

import androidx.appcompat.app.AppCompatDelegate


fun setToggleTheme(isDark: Boolean) {
    val theme = when(isDark){
        true -> AppCompatDelegate.MODE_NIGHT_NO
        false -> AppCompatDelegate.MODE_NIGHT_YES
    }
    AppCompatDelegate.setDefaultNightMode(theme)
    darkModeState.value = !isDark
}