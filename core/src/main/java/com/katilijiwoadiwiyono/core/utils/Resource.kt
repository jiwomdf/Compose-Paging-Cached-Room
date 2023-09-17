package com.katilijiwoadiwiyono.core.utils

data class Resource<T>(
    val resourceState: ResourceState,
    val data: T?
)