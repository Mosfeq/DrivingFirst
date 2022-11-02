package com.mosfeq.drivingfirstgithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        et_enterEmail.text.clear()
        et_enterPassword.text.clear()

        btn_Login.setOnClickListener {
            if (et_enterEmail.text.trim().isNotEmpty() && et_enterPassword.text.trim().isNotEmpty()){
                signInUser()
            }else{
                Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInUser(){
        auth.signInWithEmailAndPassword(et_enterEmail.text.trim().toString(), et_enterPassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }
}