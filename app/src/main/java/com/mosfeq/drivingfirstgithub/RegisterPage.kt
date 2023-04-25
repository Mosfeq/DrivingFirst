package com.mosfeq.drivingfirstgithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mosfeq.drivingfirstgithub.databinding.RegisterPageBinding
import com.mosfeq.drivingfirstgithub.learner.LearnerFragmentManager

class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        val clicked = false;

//        val margin = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )

        binding.etCreateEmail.visibility = View.INVISIBLE
        binding.etCreatePassword.visibility = View.INVISIBLE
        binding.etCreateConfirmPassword.visibility = View.INVISIBLE
        binding.btnRegister.visibility = View.INVISIBLE

        binding.etCreateEmail.text.clear()
        binding.etCreatePassword.text.clear()
        binding.etCreateConfirmPassword.text.clear()

        binding.btnRegisterAsInstructor.setOnClickListener{

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

            binding.etCreateEmail.visibility = View.VISIBLE
            binding.etCreateEmail.hint = "Email Instructor"
            binding.etCreatePassword.visibility = View.VISIBLE
            binding.etCreateConfirmPassword.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.VISIBLE
        }

        binding.btnRegisterAsLearner.setOnClickListener{

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

            binding.etCreateEmail.visibility = View.VISIBLE
            binding.etCreateEmail.hint = "Email Learner"
            binding.etCreatePassword.visibility = View.VISIBLE
            binding.etCreateConfirmPassword.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.VISIBLE
        }

        binding.btnRegister.setOnClickListener {

            if(binding.etCreateEmail.text.trim().isNotEmpty() && binding.etCreatePassword.text.trim().isNotEmpty() && binding.etCreateConfirmPassword.text.trim().isNotEmpty()){
                if(binding.etCreatePassword.text.toString() == binding.etCreateConfirmPassword.text.toString()){
                    registerUser()
                    binding.etCreateEmail.text.clear()
                    binding.etCreatePassword.text.clear()
                    binding.etCreateConfirmPassword.text.clear()
                }else{
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    Log.e("Match", "CP = ${binding.etCreatePassword}, CCP = ${binding.etCreateConfirmPassword}")
                }
            }else{
                Toast.makeText(this,"Information required", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvChangeToLoginPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser(){
        auth.createUserWithEmailAndPassword(binding.etCreateEmail.text.trim().toString(), binding.etCreatePassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Register Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LearnerFragmentManager::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Register Failed"+task.exception,Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed: ${task.exception}")
                }
            }
    }
}