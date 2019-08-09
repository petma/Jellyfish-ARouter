package com.logic.chat.contact

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.chat.R
import com.logic.chat.utils.OnItemClickListener
import me.tatarka.bindingcollectionadapter2.BR
import me.tatarka.bindingcollectionadapter2.ItemBinding

class ContactViewModel: ViewModel() {
  val items: ObservableArrayList<String>
    get() {
      val result = ObservableArrayList<String>()
      result.add("张三")
      result.add("李四")
      result.add("王五")
      result.add("张三")
      result.add("李四")
      result.add("王五")
      result.add("张三")
      result.add("李四")
      result.add("王五")
      result.add("张三")
      result.add("李四")
      result.add("王五")
      result.add("张三")
      result.add("李四")
      result.add("王五")
      result.add("张三")
      result.add("李四")
      result.add("王五")
      return result
    }

  val itemBinding: ItemBinding<String>
    get() {
      val result: ItemBinding<String> = ItemBinding.of(BR.item, R.layout.item_contact)
      result.bindExtra(BR.listener, object : OnItemClickListener {
        override fun onItemClick(item: String) {
          ARouter.getInstance().build("/chat/chatroom").navigation()
        }
      })
      return result
    }

}