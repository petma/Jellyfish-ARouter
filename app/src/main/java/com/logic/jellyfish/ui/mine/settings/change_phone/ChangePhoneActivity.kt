package com.logic.jellyfish.ui.mine.settings.change_phone

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityChangePhoneBinding

class ChangePhoneActivity : BaseActivity<ChangePhoneViewModel, ActivityChangePhoneBinding>(
  R.layout.activity_change_phone
) {

  override fun init() {
    binding.viewmodel = viewModel
    setToolbar(R.id.toolbar, R.string.change_phone_number)
  }
}