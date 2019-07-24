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
import com.logic.jellyfish.utils.getViewModel

abstract class BaseFragment<VM : ViewModel, SV : ViewDataBinding> : Fragment() {

    protected val viewModel: VM by lazy { createViewModel() }
    protected lateinit var binding: SV

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, setLayout(), null, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun initView()

    abstract fun setLayout(): Int

    private fun createViewModel(): VM {
        return ViewModelProviders.of(this).get(getViewModel(this))
    }
}

