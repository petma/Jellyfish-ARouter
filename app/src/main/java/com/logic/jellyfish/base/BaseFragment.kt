package com.logic.jellyfish.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.logic.jellyfish.utils.ClassUtils.getViewModel
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseFragment<VM : ViewModel, SV : ViewDataBinding>(
  private val layout: Int
) : Fragment(), CustomAdapt {

  protected val viewModel: VM by lazy { createViewModel() }
  protected lateinit var binding: SV

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AutoSize.autoConvertDensity(requireActivity(), 640F, false)
    binding = DataBindingUtil.inflate(inflater, layout, null, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
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
}

