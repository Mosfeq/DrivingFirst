package com.mosfeq.drivingfirstgithub

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import com.mosfeq.drivingfirstgithub.databinding.RegisterPageInstructorBinding
import com.mosfeq.drivingfirstgithub.dataClasses.Instructor
import com.mosfeq.drivingfirstgithub.learner.LearnerFragmentManager
import java.io.IOException
import java.util.Objects
import java.util.UUID

class RegisterPageAsInstructor: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterPageInstructorBinding
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
        binding = RegisterPageInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        dbFirestore = FirebaseFirestore.getInstance()
        dbInstructor =
            FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Instructors")
        name = binding.etCreateName.text.toString()
        email = binding.etCreateEmail.text.toString()

        storageReference =
            FirebaseStorage.getInstance("gs://driving-first-github.appspot.com/").reference

        binding.etCreateName.text.clear()
        binding.etCreateEmail.text.clear()
        binding.etCreatePassword.text.clear()
        binding.etCreateConfirmPassword.text.clear()


        binding.addImage.setOnClickListener() {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

        binding.btnRegisterAsInstructor.setOnClickListener {
            if (binding.etCreateEmail.text.trim().isNotEmpty()
                && binding.etCreatePassword.text.trim().isNotEmpty()
                && binding.age.text.trim().isNotEmpty()
                && binding.gender.text.trim().isNotEmpty()
                && binding.pnumber.text.trim().isNotEmpty()
                && binding.transmission.text.trim().isNotEmpty()
                && binding.price.text.trim().isNotEmpty()
                && binding.location.text.trim().isNotEmpty()
                && binding.description.text.trim().isNotEmpty()
                && selectedImage != null
            ) {
                if (binding.etCreatePassword.text.toString() == binding.etCreateConfirmPassword.text.toString()) {
                    registerAsInstructor(
                        binding.etCreateEmail.text.toString(),
                        binding.etCreateName.text.toString()
                    )
                    binding.etCreateEmail.text.clear()
                    binding.etCreatePassword.text.clear()
                    binding.etCreateConfirmPassword.text.clear()
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Information required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvChangeToLoginPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerAsInstructor(getEmail: String, getName: String) {
        auth.createUserWithEmailAndPassword(
            getEmail, binding.etCreatePassword.text.trim().toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
                registerInstructor(getEmail, getName)

            } else {
                Toast.makeText(this, "Register Failed" + task.exception, Toast.LENGTH_SHORT)
                    .show()
//                Log.e("RegisterActivity", "Failed: ${task.exception}")
            }
        }
    }

    private fun registerInstructor(getEmail: String, getName: String) {
        pdd = ProgressDialog(this@RegisterPageAsInstructor)

        pdd!!.setTitle("Uploading Data.......")
        pdd!!.show()
        val randomise = UUID.randomUUID().toString()
        val ref: StorageReference = storageReference!!.child("image/$randomise")
        Log.i("androidstudio", "addLearnerToDatabase: $ref\n$selectedImage")
        ref.putFile(selectedImage!!).addOnSuccessListener(OnSuccessListener<Any?> {
            ref.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                // Sign in success, update UI with the signed-in user's information
                dbInstructor.child("Users").child(getEmail.replace(".", "%"))
                    .setValue(
                        Instructor(
                            auth.currentUser?.uid!!,
                            binding.age.text.toString(),
                            binding.gender.text.toString(),
                            binding.pnumber.text.toString(),
                            binding.price.text.toString(),
                            binding.carModel.text.toString(),
                            binding.transmission.text.toString(),
                            binding.location.text.toString(),
                            getEmail,
                            getName,
                            uri.toString(),
                            binding.description.text.toString()
                        )
                    )
                val dialog = Dialog(this@RegisterPageAsInstructor)
                Objects.requireNonNull(dialog.window)!!
                    .setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setContentView(R.layout.prompt)
                val ok = dialog.findViewById<Button>(R.id.yes)
                val msg = dialog.findViewById<TextView>(R.id.textshow)
                msg.text = "Data Uploaded Successfully"
                ok.setOnClickListener {
                    val intent = Intent(this, LearnerFragmentManager::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                dialog.show()
            })
            Snackbar.make(
                findViewById(android.R.id.content),
                "Image Uploaded.",
                Snackbar.LENGTH_LONG
            ).show()
            pdd!!.dismiss()
        }).addOnFailureListener(OnFailureListener {
            val dialog = Dialog(this@RegisterPageAsInstructor)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                Toast.makeText(
                    this@RegisterPageAsInstructor,
                    "Image has been selected",
                    Toast.LENGTH_SHORT
                )
                    .show()
                binding.addImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}