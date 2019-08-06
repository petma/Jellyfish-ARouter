package com.logic.jellyfish.ui.mine.settings.change_phone

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityChangePhoneBinding

@Route(path = "/app/mine/settings/change_phone")
class ChangePhoneActivity : BaseActivity<ChangePhoneViewModel, ActivityChangePhoneBinding>(
  R.layout.activity_change_phone
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.change_phone_number)
  }
}