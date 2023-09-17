package com.katilijiwoadiwiyono.core.utils

sealed class ResourceState {
    object Loading : ResourceState()
    object Success : ResourceState()
    sealed class Error: ResourceState() {
        data class UnknownHost(val exception: String) : HttpErrors()
        data class GeneralError(val exception: String) : HttpErrors()
    }
    sealed class HttpErrors: ResourceState() {
        data class ResponseEmpty(val exception: String) : HttpErrors()
        data class Forbidden(val exception: String) : HttpErrors()
        data class NotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class Removed(val exception: String) : HttpErrors()
        data class RemovedResourceFoundState(val exception: String) : HttpErrors()
    }
}
