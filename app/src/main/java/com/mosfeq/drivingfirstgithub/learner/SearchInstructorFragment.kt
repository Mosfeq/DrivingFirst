package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.instructor.Instructor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

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

//        listView()
//        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rvInstructorRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.setHasFixedSize(true)
        instructorList = arrayListOf<Instructor>()

//        val instructorAdapter = InstructorAdapter(instructorList, this@SearchInstructorFragment)
//        recyclerView.adapter = instructorAdapter

        getInstructorList()

//        instructorAdapter.onItemClick = {
//            val action = SearchInstructorFragmentDirections.actionSearchInstructorFragmentToInstructorInformation()
//            findNavController().navigate(action)
//        }

    }

    override fun onClick(position: Int) {
        Toast.makeText(context, "Item Clicked at $position", Toast.LENGTH_SHORT).show()
        val action = SearchInstructorFragmentDirections.actionSearchInstructorFragmentToInstructorInformation()
        findNavController().navigate(action)
    }

    private fun getInstructorList(){
        db = FirebaseFirestore.getInstance()
        db.collection("instructors").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val instructor:Instructor? = data.toObject(Instructor::class.java)
                        if (instructor != null){
                            instructorList.add(instructor)
                        }
                    }
                    recyclerView.adapter = InstructorAdapter(instructorList, this@SearchInstructorFragment)
                }
            }
            .addOnFailureListener{
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }

//        db.collection("instructors").
//                addSnapshotListener(object: EventListener<QuerySnapshot>{
//                    override fun onEvent(
//                        value: QuerySnapshot?,
//                        error: FirebaseFirestoreException?
//                    ) {
//                        if (error != null){
//                            Log.e("Error", "Not working")
//                            return
//                        }
//
//                        for (dc: DocumentChange in value?.documentChanges!!){
//                            if (dc.type == DocumentChange.Type.ADDED){
//                                Log.e("Error", "Working 75%")
//                                instructorList.add(dc.document.toObject(Instructor::class.java))
//                            }
//                        }
//
//                        Log.e("Error", "Working but 50&")
//                        instructorAdapter.notifyDataSetChanged()
//                    }
//                })
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
            val instructor = Instructor(age[i],null, null, firstName[i], null, marketingText[i], phoneNumber[i], null, null)
            instructorList.add(instructor)
        }

    }
}