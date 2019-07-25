package com.logic.jellyfish.utils.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.logic.jellyfish.utils.AnimationUtils

@BindingAdapter("visible")
fun View.show(show: Boolean) {
   if (show && this.visibility != View.VISIBLE) {
      AnimationUtils.showAndHiddenAnimation(
         this,
         AnimationUtils.AnimationState.STATE_SHOW,
         1000
      )
   }
   if (!show && this.visibility == View.VISIBLE) {
      AnimationUtils.showAndHiddenAnimation(
         this,
         AnimationUtils.AnimationState.STATE_HIDDEN,
         300
      )
   }
}