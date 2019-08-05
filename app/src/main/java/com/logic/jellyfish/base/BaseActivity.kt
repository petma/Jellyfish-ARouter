package com.logic.jellyfish.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.logic.jellyfish.utils.ClassUtils.getViewModel
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity<out VM : ViewModel, BV : ViewDataBinding>(
  private val layout: Int
) : AppCompatActivity(), CustomAdapt {

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

  override fun isBaseOnWidth(): Boolean {
    return false
  }

  override fun getSizeInDp(): Float {
    return 640F
  }

  protected fun setToolbar(layoutId: Int, stringId: Int) {
    val toolbar = binding.root.findViewById<Toolbar>(layoutId)
    toolbar.title = getString(stringId)
    setSupportActionBar(toolbar)
  }
}

