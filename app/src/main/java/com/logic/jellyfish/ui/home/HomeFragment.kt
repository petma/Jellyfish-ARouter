package com.logic.jellyfish.ui.home

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.HomeFragmentBinding
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

  override fun init() {
    banner.apply {
      setImageLoader(object : ImageLoader() {
        override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
          Glide.with(context).load(path).into(imageView)
        }
      })
      setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
      setDelayTime(3000)
      setBannerAnimation(Transformer.DepthPage)
    }
  }


}
