package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.dataClasses.Booking
import com.mosfeq.drivingfirstgithub.databinding.FragmentTimetableLearnerBinding
import com.mosfeq.drivingfirstgithub.learner.adapters.TimetableAdapter

class TimetableLearnerFragment: Fragment(), TimetableAdapter.ClickListener {

    var rep: String = ""
    var ref: DatabaseReference? = null
    private lateinit var bookingList: ArrayList<Booking>
    var booking: Booking? = null
    private lateinit var timetableAdapter: TimetableAdapter

    private lateinit var binding: FragmentTimetableLearnerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.rvInstructorRecyclerView.layoutManager = LinearLayoutManager(context)
        bookingList = arrayListOf<Booking>()

        getTimetableData()
    }

    private fun getTimetableData() {
        rep = Preference.readString(requireActivity(), "email").toString()
        if(rep!=""){
            val a = rep.replace(".", "%")

            ref = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("booking").child(a)

            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    bookingList.clear()
                    for (instructorData in snapshot.children) {
                        booking = instructorData.getValue(Booking::class.java)
                        if (booking != null) {
                            bookingList.add(booking!!)
                        }
                    }
                    timetableAdapter = TimetableAdapter(bookingList, this@TimetableLearnerFragment)
                    binding.rvInstructorRecyclerView.adapter = timetableAdapter
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimetableLearnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(position: Int) {

    }

}