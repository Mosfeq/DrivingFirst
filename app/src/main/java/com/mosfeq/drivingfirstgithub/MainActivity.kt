package com.mosfeq.drivingfirstgithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.mosfeq.drivingfirstgithub.databinding.ActivityMainBinding
import com.mosfeq.drivingfirstgithub.instructor.InstructorFragmentManager
import com.mosfeq.drivingfirstgithub.learner.LearnerFragmentManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
        auth = FirebaseAuth.getInstance()

        binding.etEnterEmail.text.clear()
        binding.etEnterPassword.text.clear()

//        val drawable = getDrawable(R.drawable.button_hover_pressed)
//
//        btn_Login.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null)

        binding.btnLoginInstructor.setOnClickListener {
            if (binding.etEnterEmail.text.trim().isNotEmpty() && binding.etEnterPassword.text.trim().isNotEmpty()){
                signInInstructor()
            }else{
                Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLoginLearner.setOnClickListener {
            if (binding.etEnterEmail.text.trim().isNotEmpty() && binding.etEnterPassword.text.trim().isNotEmpty()){
                signInLearner()
            }else{
                Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvChangeToRegisterPage.setOnClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        binding.tvForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordPage::class.java)
            startActivity(intent)
        }

        binding.tvSkip.setOnClickListener{
            val intent = Intent(this, LearnerFragmentManager::class.java)
            startActivity(intent)
        }

    }

    private fun signInLearner(){
        auth.signInWithEmailAndPassword(binding.etEnterEmail.text.trim().toString(), binding.etEnterPassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LearnerFragmentManager::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun signInInstructor(){
        auth.signInWithEmailAndPassword(binding.etEnterEmail.text.trim().toString(), binding.etEnterPassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, InstructorFragmentManager::class.java)
                    startActivity(intent)
//                    val uid = auth.currentUser?.uid!!
//                    database.child("learner").child(uid).get().addOnSuccessListener {
//                        if (it.exists()){
//                            val role = it.child("Role").value.toString()
//                            if (role == "Learner"){
//                                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
//                                val intent = Intent(this, LearnerFragmentManager::class.java)
//                                startActivity(intent)
//                            }
//                        }
//                        else{
//                            database.child("instructor").child(uid).get().addOnSuccessListener {
//                                if (it.exists()){
//                                    val role = it.child("Role").value.toString()
//                                    if (role == "Instructor"){
//                                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
//                                        val intent = Intent(this, InstructorFragmentManager::class.java)
//                                        startActivity(intent)
//                                    }
//                                }
//                                else{
//                                    Toast.makeText(this,"Not Instructor",Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        }
//                    }
//                        .addOnFailureListener{
//                            Toast.makeText(this,"User doesn't exist",Toast.LENGTH_SHORT).show()
//                        }
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }

}