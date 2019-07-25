package com.logic.jellyfish.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    log(content)
}

fun log(content: String) {
    Log.v("测试", "\n$content\n")
}

