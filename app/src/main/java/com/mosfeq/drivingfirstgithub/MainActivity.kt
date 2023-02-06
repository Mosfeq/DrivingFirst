package com.mosfeq.drivingfirstgithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        et_enterEmail.text.clear()
        et_enterPassword.text.clear()

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
//                    if (
//                        //use realtime database to check for user role to direct them to appropriate page
//                    ){
//                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SearchInstructorPage::class.java)
                        startActivity(intent)
//                    }
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }

}