package com.logic.jellyfish.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.logic.jellyfish.R
import com.logic.jellyfish.ui.find.FindFragment
import com.logic.jellyfish.ui.home.HomeFragment
import com.logic.jellyfish.ui.message.MessageFragment
import com.logic.jellyfish.ui.mine.MineFragment
import com.logic.jellyfish.ui.sport.SportFragment
import com.logic.jellyfish.utils.ViewAnimation.fadeOutIn
import kotlinx.android.synthetic.main.main_activity.*
import me.jessyan.autosize.internal.CustomAdapt

class MainActivity : AppCompatActivity(), CustomAdapt {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    setSupportActionBar(toolbar)

    initFragment()
    initBottomNavigation()
  }

  private fun initFragment() {
    view_pager.apply {
      adapter = MainPageAdapter(supportFragmentManager)
      setNoScroll(true)
      offscreenPageLimit = 4
      addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
          position: Int,
          positionOffset: Float,
          positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
          bottom_nav.menu.getItem(position).isChecked = true
        }
      })
    }
  }

  private fun initBottomNavigation() {
    bottom_nav.setOnNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.home -> {
          changeFragment(it, 0)
        }
        R.id.sport -> {
          changeFragment(it, 1)
        }
        R.id.message -> {
          changeFragment(it, 2)
        }
        R.id.find -> {
          changeFragment(it, 3)
        }
        R.id.mine -> {
          changeFragment(it, 4)
        }
      }
      true
    }
  }

  private fun changeFragment(item: MenuItem, count: Int) {
    item.isChecked = true
    view_pager.setCurrentItem(count, false)
    fadeOutIn(view_pager)
  }


  override fun isBaseOnWidth(): Boolean {
    return false
  }

  override fun getSizeInDp(): Float {
    return 640F
  }

  inner class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
      0 -> HomeFragment()
      1 -> SportFragment()
      2 -> MessageFragment()
      3 -> FindFragment()
      4 -> MineFragment()
      else -> HomeFragment()
    }

    override fun getCount() = 5
  }

}