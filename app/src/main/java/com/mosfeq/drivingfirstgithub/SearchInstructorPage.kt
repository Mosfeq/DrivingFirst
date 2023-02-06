package com.mosfeq.drivingfirstgithub

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchInstructorPage : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_instructor_page)

        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
//        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
//        val uid = auth.currentUser?.uid
        val uid = database.push().key!!

        database.child("instructors").child(uid).child("First Name").setValue("Mark")
            .addOnCompleteListener{
                Toast.makeText(this, "Name Added", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{error ->
                Toast.makeText(this, "Error ${error.message}", Toast.LENGTH_LONG).show()
            }

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

//        if (uid != null) {
//            db.collection("learners").document(uid)
//                .get()
//                .addOnCompleteListener{ task ->
//                    if (task.isSuccessful) {
//                        val document = task.result
//                        if (document.exists()) {
//                            val email = document.getString("email")
//                            val pass = document.getString("pass")
//                            val user = document.getString("user")
//                            Log.d("TAG","$email/$pass/$user")
//                        } else {
//                            Log.d("TAG", "The document doesn't exist.")
//                        }
//                    } else {
//                        task.exception?.message?.let {
//                            Log.d("TAG", it)
//                        }
//                    }
//                }
//        }

    }
}