package com.logic.jellyfish.utils.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel> AppCompatActivity.createViewModel(clazz: Class<T>): T {
    return ViewModelProviders.of(this).get(clazz)
}

inline fun <reified T : ViewModel> AppCompatActivity.createViewModel(): T {
   return ViewModelProviders.of(this).get(T::class.java)
}