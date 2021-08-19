package com.gamesofni.kotlinTutorials.wordsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.gamesofni.kotlinTutorials.R
import com.gamesofni.kotlinTutorials.databinding.WordsappActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = WordsappActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setting up navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // setting up app bar
        setupActionBarWithNavController(navController)

    }

    // up button navigates between fragments, not activities
    override fun onSupportNavigateUp(): Boolean {
//  only if navigateUp() in navContoller fails (= is false), then the implementation in the parent
//  class will be called (short-circuit evaluation)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}