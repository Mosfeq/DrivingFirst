package com.mosfeq.drivingfirstgithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: DatabaseReference

//    private var learner = database.child("Users").get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
        auth = FirebaseAuth.getInstance()

        et_enterEmail.text.clear()
        et_enterPassword.text.clear()

//        val drawable = getDrawable(R.drawable.button_hover_pressed)
//
//        btn_Login.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null)

        btn_Login.setOnClickListener {
            if (et_enterEmail.text.trim().isNotEmpty() && et_enterPassword.text.trim().isNotEmpty()){
                signInUser()
            }else{
                Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
            }
        }

        tv_changeToRegisterPage.setOnClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        tv_forgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordPage::class.java)
            startActivity(intent)
        }

    }

    private fun signInUser(){
        auth.signInWithEmailAndPassword(et_enterEmail.text.trim().toString(), et_enterPassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    val uid = auth.currentUser?.uid!!
                    database.child("learner").child(uid).get().addOnSuccessListener {
                        if (it.exists()){
                            val role = it.child("Role").value.toString()
                            if (role == "Learner"){
                                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, SearchInstructorPage::class.java)
                                startActivity(intent)
                            }
                        }
                        else{
                            database.child("instructor").child(uid).get().addOnSuccessListener {
                                if (it.exists()){
                                    val role = it.child("Role").value.toString()
                                    if (role == "Instructor"){
                                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, InstructorPage::class.java)
                                        startActivity(intent)
                                    }
                                }
                                else{
                                    Toast.makeText(this,"Not Instructor",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                        .addOnFailureListener{
                            Toast.makeText(this,"User doesn't exist",Toast.LENGTH_SHORT).show()
                        }
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }

}