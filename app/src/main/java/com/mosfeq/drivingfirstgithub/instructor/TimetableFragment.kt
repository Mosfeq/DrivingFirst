package com.mosfeq.drivingfirstgithub.instructor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
import com.mosfeq.drivingfirstgithub.databinding.FragmentTimetableBinding
import com.mosfeq.drivingfirstgithub.learner.adapters.TimetableAdapter

class TimetableFragment : Fragment(R.layout.fragment_timetable),TimetableAdapter.ClickListener {

    var ref: DatabaseReference? = null
    var rep: String = ""
    var booking: Booking? = null
    private lateinit var bookingList: ArrayList<Booking>
    private lateinit var learnerBookingList: ArrayList<Booking>

    private lateinit var dbInstructorBooking: DatabaseReference
    private lateinit var binding: FragmentTimetableBinding
    private lateinit var instructorAdapter: TimetableInstructorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.rvInstructorRecyclerView.layoutManager = LinearLayoutManager(context)
        bookingList = arrayListOf<Booking>()
        learnerBookingList = arrayListOf<Booking>()

        getTimetableData()
    }
    private fun getTimetableData() {
        rep = Preference.readString(requireActivity(), "email").toString()
        if (rep != "") {
            ref =
                FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("booking")
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    bookingList.clear()
                    learnerBookingList.clear()
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            for (issues in issue.children) {
                                booking = issues.getValue(Booking::class.java)
                                if (booking != null) {
                                    bookingList.add(booking!!)
                                }
                            }
                        }
                    }
                    refreshListCheck()
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }
    fun refreshListCheck() {
        rep = Preference.readString(requireActivity(), "email").toString()
        for (data in bookingList) {
            if (data.emailInstructor.equals(rep)) {
                learnerBookingList.add(data)
            }
        }
        instructorAdapter = TimetableInstructorAdapter(learnerBookingList, this@TimetableFragment)
        binding.rvInstructorRecyclerView.adapter = instructorAdapter
    }

    override fun onClick(position: Int) {
        rep = Preference.readString(requireActivity(), "email").toString()

        val inputEditTextField = EditText(requireActivity())
        val dialog = AlertDialog.Builder(requireContext()).setTitle("Feedback")
            .setMessage("Leave Feedback to the client").setView(inputEditTextField)
            .setPositiveButton("Send") { _, _ ->
                val editTextInput = inputEditTextField.text.toString()
                val learner = hashMapOf(
                    "feedback" to editTextInput
                )
                dbInstructorBooking =
                    FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                        .getReference("booking").child(learnerBookingList[position].email.toString().replace(".","%"))
                        .child(learnerBookingList[position].randomKey.toString())
                dbInstructorBooking.updateChildren(learner as Map<String, Any>)
                Log.i("androidstudio", "onClick: "+ learnerBookingList[position].randomKey.toString())
            }.setNegativeButton("Cancel", null).create()
        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding.root
    }

}