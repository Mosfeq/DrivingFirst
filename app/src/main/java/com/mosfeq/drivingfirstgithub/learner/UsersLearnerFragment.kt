package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.dataClasses.Instructor
import com.mosfeq.drivingfirstgithub.learner.adapters.InstructorUsersAdapter

class UsersLearnerFragment: Fragment(R.layout.fragment_users_learner) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var instructorMessagingList: ArrayList<Instructor>
    private lateinit var adapter: InstructorUsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        instructorMessagingList = ArrayList()
        adapter = InstructorUsersAdapter(instructorMessagingList)

    }
}