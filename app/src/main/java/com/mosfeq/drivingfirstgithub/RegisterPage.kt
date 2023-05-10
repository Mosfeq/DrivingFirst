package com.mosfeq.drivingfirstgithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.mosfeq.drivingfirstgithub.databinding.RegisterPageBinding
import com.mosfeq.drivingfirstgithub.learner.Learner
import com.mosfeq.drivingfirstgithub.learner.LearnerFragmentManager

class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterPageBinding
    private lateinit var dbInstructor: DatabaseReference
    private lateinit var dbLearner: DatabaseReference
    private lateinit var dbFirestore: FirebaseFirestore

    private lateinit var name: String
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        dbFirestore = FirebaseFirestore.getInstance()
        dbInstructor =
            FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Instructors")
        dbLearner =
            FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Learners")
//        val uid = auth.currentUser?.uid!!
//        val clicked = false;

        name = binding.etCreateName.text.toString()
        email = binding.etCreateEmail.text.toString()

//        val margin = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )

//        binding.etCreateEmail.visibility = View.INVISIBLE
//        binding.etCreatePassword.visibility = View.INVISIBLE
//        binding.etCreateConfirmPassword.visibility = View.INVISIBLE
//        binding.btnRegister.visibility = View.INVISIBLE

        binding.etCreateName.text.clear()
        binding.etCreateEmail.text.clear()
        binding.etCreatePassword.text.clear()
        binding.etCreateConfirmPassword.text.clear()

//        binding.btnRegisterAsInstructor.setOnClickListener{
//
////            val tv_changePage = tv_changePage.layoutParams as ViewGroup.MarginLayoutParams
////            tv_changePage.setMargins(30, 370, 0, 0)
////            linlay_login_here.layoutParams = tv_changePage
////
////            val tv_changeToLoginPage = tv_changeToLoginPage.layoutParams as ViewGroup.MarginLayoutParams
////            tv_changeToLoginPage.setMargins(0, 370, 0, 0)
////            linlay_login_here.layoutParams = tv_changeToLoginPage
////
////            val margin_register = tv_register.layoutParams as ViewGroup.MarginLayoutParams
////            margin_register.setMargins(0, 20, 0, 70)
////            linlay_login_here.layoutParams = margin_register
//
//            binding.etCreateEmail.visibility = View.VISIBLE
//            binding.etCreateEmail.hint = "Email Instructor"
//            binding.etCreatePassword.visibility = View.VISIBLE
//            binding.etCreateConfirmPassword.visibility = View.VISIBLE
//            binding.btnRegister.visibility = View.VISIBLE
//        }
//
//        binding.btnRegisterAsLearner.setOnClickListener{
//
////            val tv_changePage = tv_changePage.layoutParams as ViewGroup.MarginLayoutParams
////            tv_changePage.setMargins(30, 360, 0, 0)
////            linlay_login_here.layoutParams = tv_changePage
////
////            val tv_changeToLoginPage = tv_changeToLoginPage.layoutParams as ViewGroup.MarginLayoutParams
////            tv_changeToLoginPage.setMargins(0, 360, 0, 0)
////            linlay_login_here.layoutParams = tv_changeToLoginPage
////
////            val margin_register = tv_register.layoutParams as ViewGroup.MarginLayoutParams
////            margin_register.setMargins(0, 20, 0, 70)
////            linlay_login_here.layoutParams = margin_register
//
//            binding.etCreateEmail.visibility = View.VISIBLE
//            binding.etCreateEmail.hint = "Email Learner"
//            binding.etCreatePassword.visibility = View.VISIBLE
//            binding.etCreateConfirmPassword.visibility = View.VISIBLE
//            binding.btnRegister.visibility = View.VISIBLE
//        }

        binding.btnRegisterAsLearner.setOnClickListener {

            if(binding.etCreateEmail.text.trim().isNotEmpty() && binding.etCreatePassword.text.trim().isNotEmpty() && binding.etCreateConfirmPassword.text.trim().isNotEmpty()){
                if(binding.etCreatePassword.text.toString() == binding.etCreateConfirmPassword.text.toString()){
                    registerLearner()
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

        binding.btnRegisterAsInstructor.setOnClickListener {

            if(binding.etCreateEmail.text.trim().isNotEmpty() && binding.etCreatePassword.text.trim().isNotEmpty() && binding.etCreateConfirmPassword.text.trim().isNotEmpty()){
                if(binding.etCreatePassword.text.toString() == binding.etCreateConfirmPassword.text.toString()){
                    registerInstructor()
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

        val change = findViewById<TextView>(R.id.tv_changeToLoginPage)

        binding.tvChangeToLoginPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
//
//
    private fun registerLearner(){
        auth.createUserWithEmailAndPassword(binding.etCreateEmail.text.trim().toString(), binding.etCreatePassword.text.trim().toString())
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Register Successful",Toast.LENGTH_SHORT).show()
                    addLearnerToDatabase()
                    val intent = Intent(this, LearnerFragmentManager::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Register Failed"+task.exception,Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Failed: ${task.exception}")
                }
            }
    }
//
    private fun registerInstructor(){
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
//
    private fun addLearnerToDatabase(){

        dbLearner.child("Users").child(auth.currentUser?.uid!!).setValue(Learner(auth.currentUser?.uid!!, binding.etCreateName.text.toString(), binding.etCreateEmail.text.toString()))

        val learner = hashMapOf(
            "name" to binding.etCreateName.text.toString(),
            "email" to binding.etCreateEmail.text.toString(),
            "uid" to auth.currentUser?.uid!!
        )

        dbFirestore.collection("learners")
            .add(learner)
            .addOnSuccessListener {documentReference ->
                Log.e("Test", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("Test", "Error adding document", e)
            }

    }

}