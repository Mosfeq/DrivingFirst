package com.mosfeq.drivingfirstgithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.register_page.*

class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        et_createEmail.text.clear()
        et_createPassword.text.clear()
        et_createConfirmPassword.text.clear()

        btn_Register.setOnClickListener {

            if(et_createEmail.text.trim().isNotEmpty() && et_createPassword.text.trim().isNotEmpty() && et_createConfirmPassword.text.trim().isNotEmpty()){
                if(et_createPassword.text.toString() == et_createConfirmPassword.text.toString()){
                    registerUser()
                    et_createEmail.text.clear()
                    et_createPassword.text.clear()
                    et_createConfirmPassword.text.clear()
                }else{
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    Log.e("Match", "CP = $et_createPassword, CCP = $et_createConfirmPassword")
                }
            }else{
                Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
            }

        }

        tv_changeToLoginPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser(){
        auth.createUserWithEmailAndPassword(et_createEmail.text.trim().toString(), et_createPassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Register Successful",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Register Failed"+task.exception,Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed: ${task.exception}")
                }
            }
    }
}