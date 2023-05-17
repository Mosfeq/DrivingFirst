package com.mosfeq.drivingfirstgithub.learner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentInstructorInformationBinding
import com.mosfeq.drivingfirstgithub.instructor.Instructor

class InstructorInformation: Fragment(R.layout.fragment_instructor_information) {

    var ref: DatabaseReference? = null
    var modelInstructor: Instructor? = null
    var rep: String = ""
    private lateinit var binding: FragmentInstructorInformationBinding
    private val args: InstructorInformationArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
//        val uid = auth.currentUser?.uid!!
        val uid = args.uid
        val a = uid!!.replace(".", "%")

        ref = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Instructors").child("Users").child(a)

        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Log.e("New Test", "Working")
                    binding.name.text = snapshot.child("name").getValue(String::class.java)
                    binding.gender.text = snapshot.child("gender").getValue(String::class.java)
                    binding.carType.text = snapshot.child("carType").getValue(String::class.java)
                    binding.phoneNo.text = snapshot.child("phone").getValue(String::class.java)
                    binding.mtype.text = snapshot.child("minformation").getValue(String::class.java)
                    binding.Email.text = snapshot.child("email").getValue(String::class.java)
                    binding.pricePerHour.text = snapshot.child("pricePerLesson").getValue(String::class.java)
                    binding.description.text = snapshot.child("description").getValue(String::class.java)
                    Glide.with(binding.img1).load(snapshot.child("uri").getValue(String::class.java)).into(binding.img1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("New Test", "Error: $error")
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



//        val name: String? = "name"
//
//        binding.tvfirstName.text = name

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

//        db.collection("instructors").document(uid!!)
//            .get()
//            .addOnCompleteListener{ task ->
//                if (task.isSuccessful) {
//                    val document = task.result
//                    if (document.exists()) {
//                        Log.e("TestNew","Tetsign")
//                        val firstName = document.getString("firstname")
//                        val age = document.get("age")
//                        binding.tvfirstName.text = firstName
//                        Log.e("TestNew", "Here: $firstName + $age")
//                    } else {
//                        Log.e("TestNew","Tetsign Fail2")
//                    }
//                } else {
//                    Log.e("TestNew","Tetsign Fail")
//                    task.exception?.message?.let {
//                        Log.e("TestNew", it)
//                    }
//                }
//            }

//    binding.tvAge.text = age.toString()
//    Log.e("TestNew","$firstName/$age")

    //                        Log.e("TestNew", "The document doesn't exist.")

//        if (uid != null) {
//
//        }

}
