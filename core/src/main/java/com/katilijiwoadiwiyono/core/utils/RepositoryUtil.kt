package com.katilijiwoadiwiyono.core.utils

import retrofit2.Response

fun <T, M> Response<T>.mapResponse(
    mapData: (T) -> M
): Resource<M> {
    return if(this.isSuccessful) {
        this.body()?.let {
            Resource.Success(mapData.invoke(it))
        } ?: kotlin.run {
            when(this.code()) {
                403 -> Resource.HttpErrors.ResourceForbidden(this.message())
                404 -> Resource.HttpErrors.ResourceNotFound(this.message())
                500 -> Resource.HttpErrors.InternalServerError(this.message())
                502 -> Resource.HttpErrors.BadGateWay(this.message())
                301 -> Resource.HttpErrors.ResourceRemoved(this.message())
                302 -> Resource.HttpErrors.RemovedResourceFound(this.message())
                else -> Resource.Error(this.message())
            }
        }
    } else {
        Resource.Error(this.message())
    }
}