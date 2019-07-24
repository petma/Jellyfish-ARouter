package com.logic.jellyfish.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast

fun View.toast(content: String) {
    Toast.makeText(this.context, content, Toast.LENGTH_LONG).show()
    log(content)
}

fun Activity.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    log(content)
}

fun log(content: String) {
    Log.v("测试", "\n$content\n")
}

