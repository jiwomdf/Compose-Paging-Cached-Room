package com.katilijiwoadiwiyono.imagegallerycompose.feature.common

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.katilijiwoadiwiyono.core.utils.ResourceState
import com.katilijiwoadiwiyono.imagegallerycompose.R
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.RedOnError
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.White

suspend fun snackBarError(
    context: Context,
    snackBarHostState: SnackbarHostState,
    resourceState: ResourceState,
    message: String? = null
) {
    val message = when(resourceState) {
        is ResourceState.Loading -> null
        is ResourceState.Success -> null
        is ResourceState.Error -> message
        is ResourceState.HttpErrors.BadGateWay -> resourceState.exception
        is ResourceState.HttpErrors.InternalServerError -> resourceState.exception
        is ResourceState.HttpErrors.RemovedResourceFoundState -> resourceState.exception
        is ResourceState.HttpErrors.Forbidden -> resourceState.exception
        is ResourceState.HttpErrors.NotFound -> resourceState.exception
        is ResourceState.HttpErrors.Removed -> resourceState.exception
        is ResourceState.Error.GeneralError -> resourceState.exception
        is ResourceState.HttpErrors.ResponseEmpty -> resourceState.exception
        is ResourceState.Error.UnknownHost -> context.getString(R.string.error_unknown_host)
    }
    message?.let {
        snackBarHostState.showSnackbar(CustomSnackbarVisuals(
            message = message,
            containerColor = RedOnError,
            contentColor = White,
        ))
    }
}