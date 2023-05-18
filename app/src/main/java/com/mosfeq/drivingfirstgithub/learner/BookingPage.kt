package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mosfeq.drivingfirstgithub.databinding.BookingPageBinding

class BookingPage: AppCompatActivity() {

    private lateinit var binding: BookingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.instructorName.text = "Instructor Name: ${intent.getStringExtra("name")}"
        binding.instructorPrice.text = "Price Per Hour: £${intent.getStringExtra("price")}"
    }

}