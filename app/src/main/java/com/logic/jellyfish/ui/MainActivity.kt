package com.logic.jellyfish.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.logic.jellyfish.R
import com.logic.jellyfish.utils.ext.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.*
import me.jessyan.autosize.internal.CustomAdapt

class MainActivity : AppCompatActivity(), CustomAdapt {

  private var currentNavController: LiveData<NavController>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    setSupportActionBar(toolbar)

//    val navController = findNavController(R.id.nav_host_container)
//    navController.addOnDestinationChangedListener { _, destination, _ ->
//      when (destination.id) {
//        R.id.loginFragment -> hideBar()
//        else -> showBar()
//      }
//    }
    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

    val navGraphIds = listOf(
      R.navigation.home,
      R.navigation.sport,
      R.navigation.message,
      R.navigation.find,
      R.navigation.mine
    )

    // Setup the bottom navigation view with a list of navigation graphs
    val controller = bottomNavigationView.setupWithNavController(
      navGraphIds = navGraphIds,
      fragmentManager = supportFragmentManager,
      containerId = R.id.nav_host_container,
      intent = intent
    )

    // Whenever the selected controller changes, setup the action bar.
    controller.observe(this, Observer { navController ->
      setupActionBarWithNavController(navController)
    })
    currentNavController = controller
  }

  override fun onSupportNavigateUp(): Boolean {
    return currentNavController?.value?.navigateUp() ?: false
  }


  override fun isBaseOnWidth(): Boolean {
    return false
  }

  override fun getSizeInDp(): Float {
    return 640F
  }

  private fun hideBar() {
    toolbar.visibility = View.GONE
    bottom_nav.visibility = View.GONE
  }

  private fun showBar() {
    toolbar.visibility = View.VISIBLE
    bottom_nav.visibility = View.VISIBLE
  }

}