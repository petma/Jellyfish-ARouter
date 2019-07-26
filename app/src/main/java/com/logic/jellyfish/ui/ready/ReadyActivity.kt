package com.logic.jellyfish.ui.ready

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ReadyActivityBinding

class ReadyActivity : BaseActivity<ReadyViewModel, ReadyActivityBinding>(R.layout.ready_activity) {

   override fun init() {
      binding.viewmodel = viewModel
   }

}