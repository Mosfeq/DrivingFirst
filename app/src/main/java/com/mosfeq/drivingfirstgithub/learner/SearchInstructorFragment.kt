package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.dataClasses.Instructor
import com.mosfeq.drivingfirstgithub.databinding.FragmentSearchInstructorBinding
import com.mosfeq.drivingfirstgithub.learner.adapters.InstructorAdapter
import java.util.Locale

class SearchInstructorFragment: Fragment(), InstructorAdapter.ClickListener {

    private lateinit var instructorAdapter: InstructorAdapter
//    private lateinit var recyclerView: RecyclerView
    private lateinit var instructorList: ArrayList<Instructor>
//    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentSearchInstructorBinding
    val filterArrayList: java.util.ArrayList<Instructor> = ArrayList<Instructor>()

    var ref: DatabaseReference? = null
    var modelInstructor: Instructor? = null
    var rep: String = ""

//    lateinit var firstName: Array<String>
//    lateinit var age: Array<Int>
//    lateinit var phoneNumber: Array<Long>
//    lateinit var marketingText: Array<String>
//    lateinit var uid: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.rvInstructorRecyclerView.layoutManager = LinearLayoutManager(context)
        getInstructorList()

        binding.searchUserOrderTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.isEmpty()) {
                    if (instructorList.isNotEmpty()) {
                        instructorAdapter.mfilterList(instructorList)
                    }
                    return
                }
                if (instructorList.isNotEmpty()) {
                    filter(editable.toString())
                }
            }
        })
        instructorList = arrayListOf<Instructor>()
    }

    fun filter(text: String) {
        filterArrayList.clear()
        for (item in instructorList) {
            try {
                if (item.name!!.toLowerCase().contains(text.lowercase(Locale.getDefault())) ||
                    item.description!!.toLowerCase().contains(text.lowercase(Locale.getDefault())) ||
                    item.pricePerLesson!!.toLowerCase().contains(text.lowercase(Locale.getDefault())) ||
                    item.carType!!.toLowerCase().contains(text.lowercase(Locale.getDefault())) ||
                    item.age!!.toLowerCase().contains(text.lowercase(Locale.getDefault())) ||
                    item.transmission!!.toLowerCase().contains(text.lowercase(Locale.getDefault()))
                ) {
                    filterArrayList.add(item)
                }
                instructorAdapter.mfilterList(filterArrayList)

            } catch (e: Exception) {
                Log.i("androidstudio", "filter: " + e)
            }

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSearchInstructorBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onClick(position: Int) {
        val selectedInstructor = instructorList[position]
        val email = selectedInstructor.email
//        val uid = selectedInstructor.uid
//        Toast.makeText(context, "Item Clicked at $position with UID: $uid", Toast.LENGTH_SHORT).show()
        val action = SearchInstructorFragmentDirections.actionSearchInstructorFragmentToInstructorInformation(email)
        findNavController().navigate(action)
    }

    private fun getInstructorList() {
        rep = Preference.readString(requireActivity(), "email").toString()
        val a = rep.replace(".", "%")

        ref = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Instructors").child("Users")

        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                instructorList.clear()
                for (instructorData in snapshot.children) {
                    modelInstructor = instructorData.getValue(Instructor::class.java)
                    if (modelInstructor != null) {
                        instructorList.add(modelInstructor!!)
                    }
                }
                instructorAdapter = InstructorAdapter(instructorList, this@SearchInstructorFragment)
                binding.rvInstructorRecyclerView.adapter = instructorAdapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}