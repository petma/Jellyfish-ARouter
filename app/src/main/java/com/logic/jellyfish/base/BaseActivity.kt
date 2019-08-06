package com.logic.jellyfish.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.logic.jellyfish.R
import com.logic.jellyfish.utils.ClassUtils.getViewModel
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity<out VM : ViewModel, BV : ViewDataBinding>(
  private val layout: Int,
  private val hasDefaultToolbar: Boolean
) : AppCompatActivity(), CustomAdapt {

  constructor(layout: Int) : this(layout, true)

  protected val viewModel: VM by lazy { createViewModel() }
  protected lateinit var binding: BV
  protected lateinit var toolbar: Toolbar
  protected lateinit var container: FrameLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 展开基础视图
    @SuppressLint("InflateParams")
    val baseView = layoutInflater.inflate(R.layout.activity_base, null)
    container = baseView.findViewById(R.id.container)
    toolbar = baseView.findViewById(R.id.toolbar)
    // 绑定DataBinding
    binding = DataBindingUtil.inflate(layoutInflater, layout, null, false)
    // 将视图添加到容器
    val view = binding.root
    val paras = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    view.layoutParams = paras
    container.addView(view)
    window.setContentView(baseView)
    // 设置默认工具栏
    if (hasDefaultToolbar) {
      setSupportActionBar(toolbar)
    } else {
      toolbar.visibility = View.GONE
    }
    // 绑定生命周期到DataBinding，用于LiveData
    binding.lifecycleOwner = this
    init()
  }

  abstract fun init()

  private fun createViewModel(): VM {
    return ViewModelProviders.of(this)[(getViewModel(this))]
  }

  override fun isBaseOnWidth(): Boolean {
    return false
  }

  override fun getSizeInDp(): Float {
    return 640F
  }

}

