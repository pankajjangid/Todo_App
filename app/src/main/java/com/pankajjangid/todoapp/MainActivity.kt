package com.pankajjangid.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        val navController = findNavController(R.id.navHostFragment)

        return navController.navigateUp() || super.navigateUpTo(upIntent)
    }


}