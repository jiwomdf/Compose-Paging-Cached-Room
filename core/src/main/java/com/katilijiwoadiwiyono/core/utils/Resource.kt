package com.katilijiwoadiwiyono.core.utils

sealed class Resource<T> {
    data class Loading<T>(val data : T): Resource<T>()
    data class Success<T>(val data : T) : Resource<T>()
    data class Error<T>(val error : String) : Resource<T>()
    sealed class HttpErrors<T>: Resource<T>() {
        data class ResourceForbidden<T>(val exception: String) : HttpErrors<T>()
        data class ResourceNotFound<T>(val exception: String) : HttpErrors<T>()
        data class InternalServerError<T>(val exception: String) : HttpErrors<T>()
        data class BadGateWay<T>(val exception: String) : HttpErrors<T>()
        data class ResourceRemoved<T>(val exception: String) : HttpErrors<T>()
        data class RemovedResourceFound<T>(val exception: String) : HttpErrors<T>()
    }
}
