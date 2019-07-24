package com.logic.jellyfish.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


inline fun <reified T : ViewModel> Fragment.createViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

fun <T : ViewModel> Fragment.createViewModel(clazz: Class<T>): T {
    return ViewModelProviders.of(this).get(clazz)
}

fun <T : ViewModel> Fragment.createSharedViewModel(clazz: Class<T>): T? {
    return this.activity?.let { ViewModelProviders.of(it).get(clazz) }
}