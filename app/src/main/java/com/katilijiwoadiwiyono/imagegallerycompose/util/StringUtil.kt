package com.katilijiwoadiwiyono.imagegallerycompose.util

object StringUtil {

    fun String.subStrTitle(maxText: Int = 20): String {
        return if(this.length >= maxText) this.substring(0, maxText) else "$this..."
    }
}