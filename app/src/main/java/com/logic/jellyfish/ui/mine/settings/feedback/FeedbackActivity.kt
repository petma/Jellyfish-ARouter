package com.logic.jellyfish.ui.mine.settings.feedback

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityFeedbackBinding

@Route(path = "/app/mine/settings/feedback")
class FeedbackActivity : BaseActivity<FeedbackViewModel, ActivityFeedbackBinding>(
    R.layout.activity_feedback
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.feedback)
  }
}