package com.logic.jellyfish.ui.mine.settings.about_us

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityAboutUsBinding

class AboutUsActivity : BaseActivity<AboutUsViewModel, ActivityAboutUsBinding>(
  R.layout.activity_about_us
) {

  override fun init() {
    binding.viewmodel = viewModel
    setToolbar(R.id.toolbar, R.string.about_us)
  }
}