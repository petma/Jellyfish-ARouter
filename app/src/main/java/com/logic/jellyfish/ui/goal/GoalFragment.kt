package com.logic.jellyfish.ui.goal

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.GoalFragmentBinding

class GoalFragment : BaseFragment<GoalViewModel, GoalFragmentBinding>() {

    override fun setLayout(): Int {
        return R.layout.goal_fragment
    }

    override fun initView() {
        binding.viewmodel = viewModel
    }

}
