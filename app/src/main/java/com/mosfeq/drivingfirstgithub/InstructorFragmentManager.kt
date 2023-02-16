package com.mosfeq.drivingfirstgithub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mosfeq.drivingfirstgithub.databinding.InstructorFragmentManagerBinding

class InstructorFragmentManager : AppCompatActivity(){
    private lateinit var navController: NavController
    private lateinit var binding: InstructorFragmentManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InstructorFragmentManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.instructor_fragment_manager) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.bottomNavbar.setupWithNavController(navController)
    }

}