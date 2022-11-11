package com.morphylix.android.junior_task_users.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragmentManager: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragmentManager =
        (supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment)
        val childFragmentManager = navHostFragmentManager.childFragmentManager

        childFragmentManager.addOnBackStackChangedListener {
            if (childFragmentManager.backStackEntryCount == 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else if (childFragmentManager.backStackEntryCount == 1) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragmentManager.navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}