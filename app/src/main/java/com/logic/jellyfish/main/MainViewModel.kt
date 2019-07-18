package com.logic.jellyfish.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.logic.jellyfish.R

class MainViewModel : ViewModel() {

    fun startRunning(view: View){
        view.findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
    }
}
