package com.logic.utils.ext

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.toast(content: String) {
  Toast.makeText(this.context, content, Toast.LENGTH_LONG).show()
}

fun Activity.toast(content: String) {
  Toast.makeText(this, content, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(content: String) {
  Toast.makeText(requireContext(), content, Toast.LENGTH_LONG).show()
}