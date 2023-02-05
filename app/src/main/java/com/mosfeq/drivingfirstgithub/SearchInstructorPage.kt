package com.mosfeq.drivingfirstgithub

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchInstructorPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_instructor_page)

        supportActionBar?.hide()

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

//        val learner = hashMapOf(
//            "first" to "Ada",
//            "last" to "Smith",
//            "born" to 1998
//        )
//
//        val instructor = hashMapOf(
//            "first" to "Alan",
//            "middle" to "Mathison",
//            "last" to "Turing",
//            "born" to 1972
//        )
//
//        db.collection("learners")
//            .add(learner)
//            .addOnSuccessListener {documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
//
//        db.collection("instructors")
//            .add(instructor)
//            .addOnSuccessListener {documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

        if (uid != null) {
            db.collection("learners").document(uid)
                .get()
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            val email = document.getString("email")
                            val pass = document.getString("pass")
                            val user = document.getString("user")
                            Log.d("TAG","$email/$pass/$user")
                        } else {
                            Log.d("TAG", "The document doesn't exist.")
                        }
                    } else {
                        task.exception?.message?.let {
                            Log.d("TAG", it)
                        }
                    }
                }
        }

    }
}