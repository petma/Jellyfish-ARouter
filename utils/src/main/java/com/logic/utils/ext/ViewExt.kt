package com.logic.utils.ext

import android.view.View
import android.widget.Toast

fun View.toast(content: String) {
  Toast.makeText(this.context, content, Toast.LENGTH_LONG).show()
}
