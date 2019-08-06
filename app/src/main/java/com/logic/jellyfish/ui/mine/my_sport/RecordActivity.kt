package com.logic.jellyfish.ui.mine.my_sport

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityRecordBinding

@Route(path = "/app/mine/my_sport")
class RecordActivity : BaseActivity<RecordViewModel, ActivityRecordBinding>(
  R.layout.activity_record
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.my_sport)
  }
}