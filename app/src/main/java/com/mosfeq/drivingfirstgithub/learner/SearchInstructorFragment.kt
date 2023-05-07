package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentSearchInstructorBinding
import com.mosfeq.drivingfirstgithub.instructor.Instructor

class SearchInstructorFragment: Fragment(R.layout.fragment_search_instructor) {

    private lateinit var binding: FragmentSearchInstructorBinding
    private lateinit var instructorAdapter: InstructorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        instructorAdapter = InstructorAdapter(mutableListOf())

        binding.rvInstructorItems.adapter = instructorAdapter
        binding.rvInstructorItems.layoutManager = LinearLayoutManager(context)

        binding.btnAddTodo.setOnClickListener {
            val todoTitle = binding.etTodoText.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Instructor(todoTitle)
                instructorAdapter.addTodo(todo)
                binding.etTodoText.text.clear()
            }
        }

        binding.btnDeleteDoneTodos.setOnClickListener {
            instructorAdapter.deleteDoneTodos()
        }

    }
}