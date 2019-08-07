package com.logic.mine.my_sport

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityRecordBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_sport")
class RecordActivity : BaseActivity<RecordViewModel, ActivityRecordBinding>(
  R.layout.activity_record
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.my_sport)
  }
}