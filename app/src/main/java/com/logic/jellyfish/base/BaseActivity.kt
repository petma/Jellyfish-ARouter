package com.logic.jellyfish.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.logic.jellyfish.utils.getViewModel

abstract class BaseActivity<out VM : ViewModel, BV : ViewDataBinding>(
   private val layout: Int
) : AppCompatActivity() {

   protected val viewModel: VM by lazy { createViewModel() }

   protected lateinit var binding: BV

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, layout)
      binding.lifecycleOwner = this
      init()
   }

   abstract fun init()

   private fun createViewModel(): VM {
      return ViewModelProviders.of(this).get(getViewModel(this))
   }
}

