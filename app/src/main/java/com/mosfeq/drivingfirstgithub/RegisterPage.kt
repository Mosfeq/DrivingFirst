package com.mosfeq.drivingfirstgithub

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mosfeq.drivingfirstgithub.databinding.RegisterPageBinding
import com.mosfeq.drivingfirstgithub.learner.Learner
import com.mosfeq.drivingfirstgithub.learner.LearnerFragmentManager
import java.util.Objects
import java.util.UUID

class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterPageBinding
    private lateinit var dbInstructor: DatabaseReference
    private lateinit var dbLearner: DatabaseReference
    private lateinit var dbFirestore: FirebaseFirestore
    var pdd: ProgressDialog? = null
    var storageReference: StorageReference? = null
    var selectedImage: Uri? = null
    val PICK_IMAGE = 1
    var bitmap: Bitmap? = null

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

        storageReference =
            FirebaseStorage.getInstance("gs://driving-first-github.appspot.com/").reference

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
        binding.addImage.setOnClickListener() {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

        binding.btnRegisterAsLearner.setOnClickListener {

            if (binding.etCreateEmail.text.trim().isNotEmpty() && binding.etCreatePassword.text.trim().isNotEmpty() && binding.etCreateConfirmPassword.text.trim().isNotEmpty()
                && selectedImage != null
            ) {
                if (binding.etCreatePassword.text.toString() == binding.etCreateConfirmPassword.text.toString()) {
                    registerLearner(
                        binding.etCreateEmail.text.toString(),
                        binding.etCreateName.text.toString()
                    )
                    binding.etCreateEmail.text.clear()
                    binding.etCreatePassword.text.clear()
                    binding.etCreateConfirmPassword.text.clear()
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    Log.e(
                        "Match",
                        "CP = ${binding.etCreatePassword}, CCP = ${binding.etCreateConfirmPassword}"
                    )
                }
            } else {
                Toast.makeText(this, "Information required", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnRegisterAsInstructor.setOnClickListener {

            if (binding.etCreateEmail.text.trim().isNotEmpty() && binding.etCreatePassword.text.trim().isNotEmpty() && binding.etCreateConfirmPassword.text.trim().isNotEmpty()
            ) {
                if (binding.etCreatePassword.text.toString() == binding.etCreateConfirmPassword.text.toString()) {
                    registerInstructor()
                    binding.etCreateEmail.text.clear()
                    binding.etCreatePassword.text.clear()
                    binding.etCreateConfirmPassword.text.clear()
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    Log.e(
                        "Match",
                        "CP = ${binding.etCreatePassword}, CCP = ${binding.etCreateConfirmPassword}"
                    )
                }
            } else {
                Toast.makeText(this, "Information required", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvRegisterPageInstructor.setOnClickListener {
            val intent = Intent(this, RegisterPageAsInstructor::class.java)
            startActivity(intent)

        }

        binding.tvChangeToLoginPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
//
//
    private fun registerLearner(getEmail: String, getName: String){
    auth.createUserWithEmailAndPassword(
        getEmail, binding.etCreatePassword.text.trim().toString()
    ).addOnCompleteListener { task ->
        if (task.isSuccessful) {

            // saving data in real time database

            /*         val realTimeUserDb = FirebaseDatabase.getInstance().reference.child("users")
                         .child(email.replace(".", "%"))
                     val userHelperClass = UserDetails(
                         binding.etCreateEmail.text.trim().toString(),
                         binding.etCreateName.text.trim().toString()
                     )
                     realTimeUserDb.setValue(userHelperClass)*/

            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            addLearnerToDatabase(getEmail, getName)

        } else {
            Toast.makeText(this, "Register Failed" + task.exception, Toast.LENGTH_SHORT)
                .show()
            Log.e("RegisterActivity", "Failed: ${task.exception}")
        }
    }
}
//
    private fun registerInstructor(){
    auth.createUserWithEmailAndPassword(
        binding.etCreateEmail.text.trim().toString(),
        binding.etCreatePassword.text.trim().toString()
    ).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LearnerFragmentManager::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Register Failed" + task.exception, Toast.LENGTH_SHORT)
                .show()
            Log.e("RegisterActivity", "Failed: ${task.exception}")
        }
    }
}
//
    private fun addLearnerToDatabase(getEmail: String, getName: String){
        pdd = ProgressDialog(this@RegisterPage)

        pdd!!.setTitle("Uploading Data.......")
        pdd!!.show()
        val randomise = UUID.randomUUID().toString()
        val ref: StorageReference = storageReference!!.child("image/$randomise")
        Log.i("androidstudio", "addLearnerToDatabase: $ref\n$selectedImage")
        ref.putFile(selectedImage!!).addOnSuccessListener(OnSuccessListener<Any?> {
            ref.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                // Sign in success, update UI with the signed-in user's information
                dbLearner.child("Users").child(getEmail.replace(".", "%"))
                    .setValue(Learner(auth.currentUser?.uid!!, getName, getEmail, uri.toString()))
                val dialog = Dialog(this@RegisterPage)
                Objects.requireNonNull(dialog.window)!!
                    .setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setContentView(R.layout.prompt)
                val ok = dialog.findViewById<Button>(R.id.yes)
                val msg = dialog.findViewById<TextView>(R.id.textshow)
                msg.text = "Data inserted Successfully"
                //                                                    egle = true;
                ok.setOnClickListener {
                    val intent = Intent(this, LearnerFragmentManager::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                dialog.show()
                val learner = hashMapOf(
                    "name" to getName,
                    "email" to getEmail,
                    "uid" to auth.currentUser?.uid!!,
                    "uri" to uri.toString()
                )
            })
            Snackbar.make(
                findViewById(android.R.id.content),
                "Image Uploaded.",
                Snackbar.LENGTH_LONG
            ).show()
            pdd!!.dismiss()
        }).addOnFailureListener(OnFailureListener {
            val dialog = Dialog(this@RegisterPage)
            Objects.requireNonNull(dialog.window)
                ?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(R.layout.prompt)
            val ok = dialog.findViewById<Button>(R.id.yes)
            val msg = dialog.findViewById<TextView>(R.id.textshow)
            msg.text = "Failed to upload"
            ok.setOnClickListener { dialog.dismiss() }
            dialog.show()
            pdd!!.dismiss()
        }).addOnProgressListener { snapshot ->
            val progresspercent: Double =
                100.00 * snapshot.bytesTransferred / snapshot.totalByteCount
            pdd!!.setMessage("Percentage: " + progresspercent.toInt() + "%")
        }

    }

}