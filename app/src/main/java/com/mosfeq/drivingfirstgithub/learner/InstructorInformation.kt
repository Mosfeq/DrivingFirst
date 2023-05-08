package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mosfeq.drivingfirstgithub.R

class InstructorInformation: Fragment(R.layout.fragment_instructor_information) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid!!

//        val instructor = hashMapOf(
//            "firstname" to "Test",
//            "phoneNumber" to 442738474758,
//            "age" to 30,
//            "marketingText" to "Test"
//        )
//
//        db.collection("instructors")
//            .add(instructor)
//            .addOnSuccessListener {documentReference ->
//                Log.d(tag, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(tag, "Error adding document", e)
//            }

    }
}