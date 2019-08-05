package com.logic.jellyfish.ui.mine.settings

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity<SettingsViewModel, ActivitySettingsBinding>(
  R.layout.activity_settings
) {

  override fun init() {
    binding.viewmodel = viewModel
    setToolbar(R.id.toolbar, R.string.setting)
  }
}