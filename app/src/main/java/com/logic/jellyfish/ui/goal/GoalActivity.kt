package com.logic.jellyfish.ui.goal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.logic.jellyfish.R
import com.logic.jellyfish.databinding.GoalActivityBinding
import com.logic.jellyfish.utils.ext.createViewModel

class GoalActivity : AppCompatActivity() {

    private val viewModel: GoalViewModel by lazy { createViewModel<GoalViewModel>() }
    private lateinit var binding: GoalActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.goal_activity)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@GoalActivity
        }
    }

}
