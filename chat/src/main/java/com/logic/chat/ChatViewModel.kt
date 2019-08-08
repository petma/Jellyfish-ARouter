package com.logic.chat

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import me.tatarka.bindingcollectionadapter2.BR
import me.tatarka.bindingcollectionadapter2.ItemBinding

class ChatViewModel : ViewModel() {

  val items = ObservableArrayList<String>()
  val itemBinding: ItemBinding<String> = ItemBinding.of(BR.item, R.layout.item_message)

  init {
    itemBinding.bindExtra(BR.listener, object : OnItemClickListener {
      override fun onItemClick(item: String) {
        ARouter.getInstance().build("/chat/chatroom").navigation()
      }
    })

    items.add("张三")
    items.add("李四")
    items.add("王五")
    items.add("张三")
    items.add("李四")
    items.add("王五")
    items.add("张三")
    items.add("李四")
    items.add("王五")
    items.add("张三")
    items.add("李四")
    items.add("王五")
    items.add("张三")
    items.add("李四")
    items.add("王五")
    items.add("张三")
    items.add("李四")
    items.add("王五")
  }
}