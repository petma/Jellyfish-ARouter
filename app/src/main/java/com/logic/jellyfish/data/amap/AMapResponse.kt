package com.logic.jellyfish.data.amap

data class AMapResponse<T>(
    var data: T? = null,
    var errcode: Int? = null,
    var errdetail: String? = null,
    var errmsg: String? = null
)