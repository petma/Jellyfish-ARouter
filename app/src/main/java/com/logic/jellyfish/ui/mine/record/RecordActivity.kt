package com.logic.jellyfish.ui.mine.record

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityRecordBinding

class RecordActivity : BaseActivity<RecordViewModel, ActivityRecordBinding>(
  R.layout.activity_record
) {

  override fun init() {
    binding.viewmodel = viewModel
  }
}