package com.logic.jellyfish.utils

import android.content.Intent
import android.view.View
import android.widget.Toast

fun View.toast(content: String) {
   Toast.makeText(this.context, content, Toast.LENGTH_LONG).show()
   log(content)
}

inline fun <reified T> View.navigateTo() {
   this.context.startActivity(Intent(this.context, T::class.java))
}