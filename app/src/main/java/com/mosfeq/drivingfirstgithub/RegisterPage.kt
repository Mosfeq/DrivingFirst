package com.mosfeq.drivingfirstgithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

        val clicked = false;

//        val margin = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )

        et_createEmail.visibility = View.GONE
        et_createPassword.visibility = View.GONE
        et_createConfirmPassword.visibility = View.GONE
        btn_Register.visibility = View.GONE

        et_createEmail.text.clear()
        et_createPassword.text.clear()
        et_createConfirmPassword.text.clear()

        btn_RegisterAsInstructor.setOnClickListener{

//            val tv_changePage = tv_changePage.layoutParams as ViewGroup.MarginLayoutParams
//            tv_changePage.setMargins(30, 370, 0, 0)
//            linlay_login_here.layoutParams = tv_changePage
//
//            val tv_changeToLoginPage = tv_changeToLoginPage.layoutParams as ViewGroup.MarginLayoutParams
//            tv_changeToLoginPage.setMargins(0, 370, 0, 0)
//            linlay_login_here.layoutParams = tv_changeToLoginPage
//
//            val margin_register = tv_register.layoutParams as ViewGroup.MarginLayoutParams
//            margin_register.setMargins(0, 20, 0, 70)
//            linlay_login_here.layoutParams = margin_register

            et_createEmail.visibility = View.VISIBLE
            et_createEmail.hint = "Email Instructor"
            et_createPassword.visibility = View.VISIBLE
            et_createConfirmPassword.visibility = View.VISIBLE
            btn_Register.visibility = View.VISIBLE
        }

        btn_RegisterAsLearner.setOnClickListener{

//            val tv_changePage = tv_changePage.layoutParams as ViewGroup.MarginLayoutParams
//            tv_changePage.setMargins(30, 360, 0, 0)
//            linlay_login_here.layoutParams = tv_changePage
//
//            val tv_changeToLoginPage = tv_changeToLoginPage.layoutParams as ViewGroup.MarginLayoutParams
//            tv_changeToLoginPage.setMargins(0, 360, 0, 0)
//            linlay_login_here.layoutParams = tv_changeToLoginPage
//
//            val margin_register = tv_register.layoutParams as ViewGroup.MarginLayoutParams
//            margin_register.setMargins(0, 20, 0, 70)
//            linlay_login_here.layoutParams = margin_register

            et_createEmail.visibility = View.VISIBLE
            et_createEmail.hint = "Email Learner"
            et_createPassword.visibility = View.VISIBLE
            et_createConfirmPassword.visibility = View.VISIBLE
            btn_Register.visibility = View.VISIBLE
        }

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
                    val intent = Intent(this, SearchInstructorPage::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Register Failed"+task.exception,Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed: ${task.exception}")
                }
            }
    }
}