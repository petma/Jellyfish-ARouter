package com.logic.jellyfish.utils.ext

import android.content.Intent
import android.view.View
import android.widget.Toast

fun View.toast(content: String) {
   Toast.makeText(this.context, content, Toast.LENGTH_LONG).show()
   log(content)
}

inline fun <reified T> View.startActivity() {
   this.context.startActivity(Intent(this.context, T::class.java))
}

inline fun <reified T> View.startService() {
   this.context.startService(Intent(this.context, T::class.java))
}

inline fun <reified T> View.startForegroundService() {
   this.context.startForegroundService(Intent(this.context, T::class.java))
}

