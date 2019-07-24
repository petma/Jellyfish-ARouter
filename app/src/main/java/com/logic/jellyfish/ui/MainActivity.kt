package com.logic.jellyfish.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.logic.jellyfish.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

//        val navController = findNavController(R.id.nav_host_fragment)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when(destination.id){
//                R.id.mainFragment->{
//                    toolbar.visibility = View.GONE
//                }
//                else -> {
//                    toolbar.visibility = View.VISIBLE
//                }
//            }
//        }

    }
}