package com.logic.jellyfish.ui.timer

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.TimerFragmentBinding

class TimerFragment : BaseFragment<TimerViewModel, TimerFragmentBinding>() {

    override fun setLayout(): Int {
        return R.layout.timer_fragment
    }

    override fun initView() {
        binding.viewmodel = viewModel
    }


}