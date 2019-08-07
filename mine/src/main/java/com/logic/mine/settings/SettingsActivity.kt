package com.logic.mine.settings

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivitySettingsBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/settings")
class SettingsActivity : BaseActivity<SettingsViewModel, ActivitySettingsBinding>(
  R.layout.activity_settings
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.setting)
  }
}