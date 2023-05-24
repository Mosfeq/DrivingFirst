package com.mosfeq.drivingfirstgithub

import android.Manifest
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: DatabaseReference

    lateinit var signInCheck: String
    lateinit var users: String
    lateinit var role: String

    private lateinit var dbLearner: DatabaseReference
    var roleCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        supportActionBar?.hide()

        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
        auth = FirebaseAuth.getInstance()

        binding.etEnterEmail.text.clear()
        binding.etEnterPassword.text.clear()

        signInCheck = Preference.readString(this, "email").toString()
        users = Preference.readString(this, "role").toString()
        if (signInCheck != "") {
            if (users != "") {
                if (users == "learner") {
                    val intent = Intent(this, LearnerFragmentManager::class.java)
                    startActivity(intent)
                }
                if (users == "instructor") {
                    val intent = Intent(this, InstructorFragmentManager::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEnterEmail.text.trim().isNotEmpty() && binding.etEnterPassword.text.trim().isNotEmpty()){
                signIn()
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
    }

    private fun signIn(){
        auth.signInWithEmailAndPassword(
            binding.etEnterEmail.text.trim().toString(),
            binding.etEnterPassword.text.trim().toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                Preference.writeString(
                    this@MainActivity, "email", binding.etEnterEmail.text.toString()
                )
                getUserData(binding.etEnterEmail.text.toString())
            } else {
                Toast.makeText(
                    this, "Authentication Error" + task.exception, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getUserData(emailCheck: String) {
        dbLearner =
            FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Learners").child("Users").child(emailCheck.replace(".", "%"))

        dbLearner!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                roleCheck = if (snapshot.exists()) {
                    Preference.writeString(
                        this@MainActivity, "role", "learner"
                    )
                    callRole(true)
                    true
                } else {
                    Preference.writeString(
                        this@MainActivity, "role", "instructor"
                    )
                    callRole(false)
                    false
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun callRole(getRole: Boolean){
        if (getRole) {
            val intent = Intent(this, LearnerFragmentManager::class.java)
            startActivity(intent)
        }
        if (!getRole) {
            val intent = Intent(this, InstructorFragmentManager::class.java)
            startActivity(intent)
        }
    }

    private fun requestPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                }
                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }
}