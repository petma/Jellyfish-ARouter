package com.logic.jellyfish.ui.message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.FragmentMessageBinding

@Route(path = "/app/message")
class MessageFragment :
  BaseFragment<MessageViewModel, FragmentMessageBinding>(R.layout.fragment_message) {

  override fun init() {
  }


}
