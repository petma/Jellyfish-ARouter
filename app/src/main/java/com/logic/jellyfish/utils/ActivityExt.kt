package com.logic.jellyfish.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel> AppCompatActivity.createViewModel(clazz: Class<T>): T {
    return ViewModelProviders.of(this).get(clazz)
}