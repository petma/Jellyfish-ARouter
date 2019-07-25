package com.logic.jellyfish.ui.ready

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.logic.jellyfish.R
import com.logic.jellyfish.databinding.ReadyActivityBinding
import com.logic.jellyfish.utils.ext.createViewModel

class ReadyActivity : AppCompatActivity() {

   private val viewModel: ReadyViewModel by lazy { createViewModel<ReadyViewModel>() }
   private lateinit var binding: ReadyActivityBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.ready_activity)
      binding.apply {
         viewmodel = viewModel
         lifecycleOwner = this@ReadyActivity
      }
   }
}