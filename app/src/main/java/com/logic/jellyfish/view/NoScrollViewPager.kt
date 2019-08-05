package com.logic.jellyfish.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

  private var noScroll = false

  fun setNoScroll(noScroll: Boolean) {
    this.noScroll = noScroll
  }

  override fun onTouchEvent(arg0: MotionEvent): Boolean {
    /* return false;//super.onTouchEvent(arg0); */
    return if (noScroll)
      false
    else
      super.onTouchEvent(arg0)
  }

  override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
    return if (noScroll)
      false
    else
      super.onInterceptTouchEvent(arg0)
  }

}