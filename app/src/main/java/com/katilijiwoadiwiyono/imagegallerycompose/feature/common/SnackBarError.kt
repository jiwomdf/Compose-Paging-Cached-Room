package com.katilijiwoadiwiyono.imagegallerycompose.feature.common

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.imagegallerycompose.R

suspend fun <T> snackBarError(
    context: Context,
    snackBarHostState: SnackbarHostState,
    resource: Resource<T>,
    message: String? = null
) {
    val message = when(resource) {
        is Resource.Loading -> null
        is Resource.Success -> null
        is Resource.Error -> message
        is Resource.HttpErrors.BadGateWay -> context.getString(R.string.app_name)
        is Resource.HttpErrors.InternalServerError -> context.getString(R.string.app_name)
        is Resource.HttpErrors.RemovedResourceFound -> context.getString(R.string.app_name)
        is Resource.HttpErrors.ResourceForbidden -> context.getString(R.string.app_name)
        is Resource.HttpErrors.ResourceNotFound -> context.getString(R.string.app_name)
        is Resource.HttpErrors.ResourceRemoved -> context.getString(R.string.app_name)
    }
    message?.let {
        snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
    }
}