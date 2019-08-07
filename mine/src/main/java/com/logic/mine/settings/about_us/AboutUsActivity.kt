package com.logic.mine.settings.about_us

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityAboutUsBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/settings/about_us")
class AboutUsActivity : BaseActivity<AboutUsViewModel, ActivityAboutUsBinding>(
  R.layout.activity_about_us
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.about_us)
  }
}