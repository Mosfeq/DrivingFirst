package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mosfeq.drivingfirstgithub.R

class InstructorInformation: Fragment(R.layout.fragment_instructor_information) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }
}