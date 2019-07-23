package com.logic.jellyfish.base


import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.logic.jellyfish.utils.ClassUtils

abstract class BaseActivity<VM : ViewModel, SV : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: SV

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = DataBindingUtil.setContentView(this, setLayout())
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(ClassUtils.getViewModel(this))
        initView()
    }

    abstract fun setLayout(): Int

    abstract fun initView()

}

