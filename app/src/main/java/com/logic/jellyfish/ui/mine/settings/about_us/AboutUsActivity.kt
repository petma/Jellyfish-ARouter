package com.logic.jellyfish.ui.mine.settings.about_us

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityAboutUsBinding

@Route(path = "/app/mine/settings/about_us")
class AboutUsActivity : BaseActivity<AboutUsViewModel, ActivityAboutUsBinding>(
  R.layout.activity_about_us
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.about_us)
  }
}