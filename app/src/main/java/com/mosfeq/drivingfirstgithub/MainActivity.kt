package com.mosfeq.drivingfirstgithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        val learner = uid?.let {
            db.collection("learners").document(it).get()
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            val role = document.getString("learner")
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

        val test = if

        val instructor = uid?.let {
            db.collection("instructors").document(it).get()
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            val role = document.getString("instructor")
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

        et_enterEmail.text.clear()
        et_enterPassword.text.clear()

        if (uid != null) {
            db.collection("learners").document(uid)
                .get()
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            val role = document.getString("learner")
                            btn_Login.setOnClickListener {
                                if (et_enterEmail.text.trim().isNotEmpty() && et_enterPassword.text.trim().isNotEmpty()){
                                    signInUser(role)
                                }else{
                                    Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
                                }
                            }
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


        tv_changeToRegisterPage.setOnClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        tv_forgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordPage::class.java)
            startActivity(intent)
        }

    }

    private fun loginLearner(){
        val intent = Intent(this, SearchInstructorPage::class.java)
        startActivity(intent)
    }

    private fun loginInstructor(){
        val intent = Intent(this, ForgotPasswordPage::class.java)
        startActivity(intent)
    }

    private fun signInUser(string: String?){
        auth.signInWithEmailAndPassword(et_enterEmail.text.trim().toString(), et_enterPassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    loginLearner()
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }

}