package com.logic.jellyfish.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.activity_main.*
import me.jessyan.autosize.internal.CustomAdapt

class MainActivity : AppCompatActivity(), CustomAdapt {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    initFragment()
    initBottomNavigation()
  }

  private fun initFragment() {
    val fragments = arrayOf<Fragment>(
      HomeFragment(),
      SportFragment(),
      MessageFragment(),
      FindFragment(),
      MineFragment()
    )
    view_pager.apply {
      adapter = MainPageAdapter(fragments, supportFragmentManager)
      offscreenPageLimit = 3
      setNoScroll(true)
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

  class MainPageAdapter(
    private val fragments: Array<Fragment>,
    fm: FragmentManager
  ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = 5
  }

}