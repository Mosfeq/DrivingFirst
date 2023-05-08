package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentInstructorInformationBinding

class InstructorInformation: Fragment(R.layout.fragment_instructor_information) {

    private lateinit var binding: FragmentInstructorInformationBinding
    private val args: InstructorInformationArgs by navArgs()
//    private val args: InstructorInformationArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
//        val uid = auth.currentUser?.uid!!
        val uid = args.uid

//        val instructor = hashMapOf(
//            "firstname" to "David",
//            "lastname" to "Bale",
//            "email" to "dbale30@gmail.com",
//            "phoneNumber" to 447384728193,
//            "age" to 30,
//            "price" to 25,
//            "gender" to "male",
//            "marketingText" to ""
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

        db.collection("instructors").document(uid!!)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        Log.e("TestNew","Tetsign")
                        val firstName = document.getString("firstname")
                        val age = document.get("age")
//                        binding.tvfirstName.text = firstName.toString()
                        Log.e("TestNew", "Here: $firstName + $age")
                    } else {
                        Log.e("TestNew","Tetsign Fail2")
                    }
                } else {
                    Log.e("TestNew","Tetsign Fail")
                    task.exception?.message?.let {
                        Log.e("TestNew", it)
                    }
                }
            }

//    binding.tvAge.text = age.toString()
//    Log.e("TestNew","$firstName/$age")

    //                        Log.e("TestNew", "The document doesn't exist.")

//        if (uid != null) {
//
//        }

    }
}