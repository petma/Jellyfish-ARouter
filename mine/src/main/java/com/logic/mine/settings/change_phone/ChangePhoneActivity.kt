package com.logic.mine.settings.change_phone

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityChangePhoneBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/settings/change_phone")
class ChangePhoneActivity : BaseActivity<ChangePhoneViewModel, ActivityChangePhoneBinding>(
  R.layout.activity_change_phone
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.change_phone_number)
  }
}