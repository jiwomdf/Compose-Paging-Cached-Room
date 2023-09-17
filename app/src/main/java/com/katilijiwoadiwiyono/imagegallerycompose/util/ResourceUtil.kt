package com.katilijiwoadiwiyono.imagegallerycompose.util

import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow

fun <T>MutableStateFlow<T>.setSuccess(data: T){
    this.value = data
}

fun <T>MutableStateFlow<T>.setFailed(data: T){
    this.value = data
}

fun <T>MutableStateFlow<T>.setFailed(message: String){
    this.value = Resource(ResourceState.Error.GeneralError(message), null) as T
}