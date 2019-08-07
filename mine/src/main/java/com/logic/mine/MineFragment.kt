package com.logic.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment(), View.OnClickListener {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_mine, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    settings.setOnClickListener(this)
    following.setOnClickListener(this)
    record.setOnClickListener(this)
    event.setOnClickListener(this)
    message.setOnClickListener(this)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.settings -> {
        ARouter.getInstance().build("/mine/settings").navigation()
      }
      R.id.message -> {
        ARouter.getInstance().build("/mine/my_message").navigation()
      }
      R.id.event -> {
        ARouter.getInstance().build("/mine/my_event").navigation()
      }
      R.id.record -> {
        ARouter.getInstance().build("/mine/my_sport").navigation()
      }
      R.id.following -> {
        ARouter.getInstance().build("/mine/my_following").navigation()
      }
    }
  }

}
