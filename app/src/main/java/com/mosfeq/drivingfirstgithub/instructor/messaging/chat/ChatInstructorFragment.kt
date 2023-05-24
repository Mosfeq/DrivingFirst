package com.mosfeq.drivingfirstgithub.instructor.messaging.chat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mosfeq.drivingfirstgithub.R

class ChatInstructorFragment: Fragment(R.layout.fragment_chat_instructor) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()


    }

}