package com.logic.jellyfish.data.amap

data class AMapResponse<T>(
    val data: T?,
    val errcode: Int?,
    val errdetail: String?,
    val errmsg: String?
)