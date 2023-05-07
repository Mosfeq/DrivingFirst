package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentSearchInstructorBinding
import com.mosfeq.drivingfirstgithub.instructor.Instructor

class SearchInstructorFragment: Fragment(R.layout.fragment_search_instructor) {

    private lateinit var binding: FragmentSearchInstructorBinding
    private lateinit var instructorAdapter: InstructorAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var instructorList: ArrayList<Instructor>

    lateinit var firstName: Array<String>
    lateinit var lastName: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        listView()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rvInstructorRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        instructorAdapter = InstructorAdapter(instructorList)
        recyclerView.adapter = instructorAdapter

    }

//    private fun instructorList(listSize: Int): ArrayList<Instructor>{
//        //The datatype after the colon in a function is its return type, so ArrayList<ExampleItem> in this case
//        val list = ArrayList<Instructor>()
//
//        for (i in 0 until listSize){
//            val item = Instructor(null, "firstName $i", "LastName")
//            list += item
//        }
//        return list
//    }
    private fun listView(){

        val list: Int = 15
        instructorList = arrayListOf<Instructor>()

        firstName = arrayOf(
            "Mark",
            "John",
            "Bob",
            "Terry",
            "Merlin",
            "Test1",
            "Test2",
            "Test3",
            "Test4",
            "Test5",
            "Test6",
            "Test7",
            "Test8",
            "Test9",
            "Test10"
        )

        lastName = arrayOf(
            "Rover",
            "Lennon",
            "Wheeler",
            "Smith",
            "Martel",
            "Test1",
            "Test2",
            "Test3",
            "Test4",
            "Test5",
            "Test6",
            "Test7",
            "Test8",
            "Test9",
            "Test10"
        )

        for (i in 0 until list){
            val instructor = Instructor(null, firstName[i], lastName[i])
            instructorList.add(instructor)
        }

    }
}