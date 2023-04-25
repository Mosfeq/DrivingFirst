package com.mosfeq.drivingfirstgithub.instructor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mosfeq.drivingfirstgithub.R


class UsersFragment : Fragment(R.layout.fragment_users) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

}