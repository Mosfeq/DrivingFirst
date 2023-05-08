package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.instructor.Instructor
import com.google.firebase.firestore.FirebaseFirestore

class SearchInstructorFragment: Fragment(R.layout.fragment_search_instructor), InstructorAdapter.ClickListener {

    private lateinit var instructorAdapter: InstructorAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var instructorList: ArrayList<Instructor>
    private lateinit var db: FirebaseFirestore

    lateinit var firstName: Array<String>
    lateinit var age: Array<Int>
    lateinit var phoneNumber: Array<Long>
    lateinit var marketingText: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        listView()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rvInstructorRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val instructorAdapter = InstructorAdapter(instructorList, this@SearchInstructorFragment)
        recyclerView.adapter = instructorAdapter

//        instructorAdapter.onItemClick = {
//            val action = SearchInstructorFragmentDirections.actionSearchInstructorFragmentToInstructorInformation()
//            findNavController().navigate(action)
//        }

    }

    override fun onClick(position: Int) {
        Toast.makeText(context, "Item Clicked at $position", Toast.LENGTH_SHORT).show()
//        val action = SearchInstructorFragmentDirections.actionSearchInstructorFragmentToInstructorInformation()
//        findNavController().navigate(action)
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

        val name: String = "Bob"
        val list: Int = 10
        instructorList = arrayListOf<Instructor>()

        firstName = arrayOf(
            "Mark",
            "John",
            name,
            "Terry",
            "Merlin",
            "Test1",
            "Test2",
            "Test3",
            "Test4",
            "Test5",
        )

        marketingText = arrayOf(
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
        )

        age = arrayOf(
            30,
            20,
            10,
            15,
            47,
            52,
            73,
            42,
            35,
            64
        )

        phoneNumber = arrayOf(
            447700185214,
            447700185215,
            447700185216,
            447700185217,
            447700185218,
            447700185219,
            447700185220,
            447700185221,
            447700185222,
            447700185223,
        )

        for (i in 0 until list){
            val instructor = Instructor(null, firstName[i], null, null, phoneNumber[i], age[i], null, marketingText[i])
            instructorList.add(instructor)
        }

    }
}