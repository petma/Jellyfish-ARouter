package com.logic.jellyfish.ui.mine.settings.feedback

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityFeedbackBinding

class FeedbackActivity : BaseActivity<FeedbackViewModel, ActivityFeedbackBinding>(
  R.layout.activity_feedback
) {

  override fun init() {
    binding.viewmodel = viewModel
    setToolbar(R.id.toolbar, R.string.feedback)
  }
}