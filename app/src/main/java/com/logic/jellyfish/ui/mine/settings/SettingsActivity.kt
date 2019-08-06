package com.logic.jellyfish.ui.mine.settings

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivitySettingsBinding

@Route(path = "/app/mine/settings")
class SettingsActivity : BaseActivity<SettingsViewModel, ActivitySettingsBinding>(
  R.layout.activity_settings
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.setting)
  }
}