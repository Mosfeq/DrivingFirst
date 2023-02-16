package com.mosfeq.drivingfirstgithub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mosfeq.drivingfirstgithub.databinding.ForgotPasswordPageBinding

class ForgotPasswordPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ForgotPasswordPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgotPasswordPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnResetPassword.setOnClickListener{
            if (binding.etEmailForReset.text.trim().isNotEmpty()){
                resetPassword()
            }else{
                Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvFpchangeToLoginPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun resetPassword(){
        val email: String = binding.etEmailForReset.text.toString().trim {it <= ' '}
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