package com.mosfeq.drivingfirstgithub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.forgot_password_page.*

class ForgotPasswordPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_page)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        btn_resetPassword.setOnClickListener{
            if (et_emailForReset.text.trim().isNotEmpty()){
                resetPassword()
            }else{
                Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun resetPassword(){
        val email: String = et_emailForReset.text.toString().trim {it <= ' '}
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Email sent to reset your password", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Authentication Error"+task.exception,Toast.LENGTH_SHORT).show()
                }
            }
    }
}