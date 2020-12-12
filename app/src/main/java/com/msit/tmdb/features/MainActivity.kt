package com.msit.tmdb.features

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import androidx.navigation.findNavController
import com.msit.tmdb.R
import com.msit.tmdb.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    private val topFragments = listOf(R.id.movies, R.id.tvShows, R.id.people)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    fun initUi() {
        binding.apply {

            NavigationUI.setupWithNavController(bottomNavView, navController)

            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                bottomNavView.visibility = if (topFragments.contains(destination.id)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}