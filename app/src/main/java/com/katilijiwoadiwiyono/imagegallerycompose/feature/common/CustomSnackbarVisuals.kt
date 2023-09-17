package com.katilijiwoadiwiyono.imagegallerycompose.feature.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.graphics.Color

data class CustomSnackbarVisuals(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val containerColor: Color = Color.White,
    val contentColor: Color = Color.Red,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
): SnackbarVisuals
