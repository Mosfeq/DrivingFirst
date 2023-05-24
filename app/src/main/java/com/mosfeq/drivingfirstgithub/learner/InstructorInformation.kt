package com.mosfeq.drivingfirstgithub.learner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentInstructorInformationBinding
import com.mosfeq.drivingfirstgithub.learner.messaging.UserGroupViewModel

class InstructorInformation: Fragment(R.layout.fragment_instructor_information) {

    var ref: DatabaseReference? = null
    var rep: String = ""
    var pricePerHour: String = ""
    var instructorEmail: String = ""
    var instructorName: String = ""
    var learnerName: String = ""
    var learnerUri: String = ""
    var instructorUri: String = ""
    private lateinit var dbLearner: DatabaseReference

    private val userGroupViewModel: UserGroupViewModel by viewModels()
    private lateinit var binding: FragmentInstructorInformationBinding
    private val args: InstructorInformationArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
//        val uid = auth.currentUser?.uid!!
        val uid = args.uid
        rep = Preference.readString(requireActivity(), "email").toString()
        val a = uid!!.replace(".", "%")

        ref = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Instructors").child("Users").child(a)

        getLearnerData()
        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    binding.name.text = snapshot.child("name").getValue(String::class.java)
                    binding.gender.text = snapshot.child("gender").getValue(String::class.java)
                    binding.carType.text = snapshot.child("carType").getValue(String::class.java)
                    binding.phoneNo.text = snapshot.child("phone").getValue(String::class.java)
                    binding.transmission.text = snapshot.child("transmission").getValue(String::class.java)
                    binding.Email.text = snapshot.child("email").getValue(String::class.java)
                    binding.pricePerHour.text = snapshot.child("pricePerLesson").getValue(String::class.java)
                    binding.description.text = snapshot.child("description").getValue(String::class.java)
                    Glide.with(binding.img1).load(snapshot.child("uri").getValue(String::class.java)).into(binding.img1)
                    instructorName = snapshot.child("name").getValue(String::class.java).toString()
                    instructorEmail = snapshot.child("email").getValue(String::class.java).toString()
                    pricePerHour = snapshot.child("pricePerLesson").getValue(String::class.java).toString()
                    instructorUri = snapshot.child("uri").getValue(String::class.java).toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("New Test", "Error: $error")
            }
        })

        binding.booking.setOnClickListener {
            if (Preference.readString(requireActivity(), "email") == "") {
                Toast.makeText(requireActivity(), "Sign in required", Toast.LENGTH_LONG).show()
            }else {
                val intent = Intent(requireActivity(), BookingPage::class.java)
                intent.putExtra("price", pricePerHour)
                intent.putExtra("instructorEmail", instructorEmail)
                intent.putExtra("instructorName", instructorName)
                intent.putExtra("instructorUri", instructorUri)
                intent.putExtra("learnerName", learnerName)
                intent.putExtra("learnerUri", learnerUri)
                startActivity(intent)
            }
        }

        binding.message.setOnClickListener() {
            userGroupViewModel.addNewGroup(
                instructorName,
                learnerName,
                a,
                (Preference.readString(requireActivity(), "email").toString()),
                instructorUri,
                learnerUri
            )
//            Toast.makeText(requireActivity(), "Chat added!", Toast.LENGTH_SHORT).show()
            val action = InstructorInformationDirections.actionInstructorInformationToUsersLearnerFragment()
            findNavController().navigate(action)
        }

    }
    private fun getLearnerData() {
        dbLearner =
            FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Learners").child("Users").child(rep.replace(".", "%"))

        dbLearner!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                learnerName = snapshot.child("name").getValue(String::class.java).toString()
                learnerUri = snapshot.child("uri").getValue(String::class.java).toString()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentInstructorInformationBinding.inflate(inflater, container, false)
        return binding.root
    }
}
