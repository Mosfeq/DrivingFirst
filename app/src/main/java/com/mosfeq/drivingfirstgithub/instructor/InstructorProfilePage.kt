package com.mosfeq.drivingfirstgithub.instructor

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentInstructorProfilePageBinding
import java.io.IOException
import java.util.UUID

class InstructorProfilePage: Fragment(R.layout.fragment_instructor_profile_page) {

    lateinit var uid: String
    lateinit var email: String
    lateinit var uris: String
    lateinit var name: String
    lateinit var gender: String
    lateinit var transmission: String
    lateinit var description: String
    lateinit var pricePerLesson: String
    lateinit var age: String
    lateinit var phone: String
    lateinit var cartype: String
    lateinit var location: String

    lateinit var signInCheck: String
    lateinit var replaceEmail: String
    private lateinit var dbLearner: DatabaseReference
    private lateinit var binding: FragmentInstructorProfilePageBinding


    var selectedImage: Uri? = null
    val PICK_IMAGE = 1
    var bitmap: Bitmap? = null
    var pdd: ProgressDialog? = null
    var storageReference: StorageReference? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        signInCheck = Preference.readString(requireActivity(), "email").toString()
        if (signInCheck != "") {
            replaceEmail = signInCheck
            storageReference =
                FirebaseStorage.getInstance("gs://driving-first-github.appspot.com/").reference

            if (signInCheck != "") {
                replaceEmail.replace(".", "%")
                getUserData()
            }
        }

        binding.btnUpdate.setOnClickListener() {
            if (binding.etCreateEmail.text.trim().isNotEmpty()) {
                updateLearnerToDatabase()
            }
        }

        binding.addImage.setOnClickListener() {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, PICK_IMAGE)
        }

    }

    private fun getUserData() {
        dbLearner =
            FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Instructors").child("Users").child(replaceEmail.replace(".", "%"))

        dbLearner!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email = snapshot.child("email").getValue(String::class.java).toString()
                name = snapshot.child("name").getValue(String::class.java).toString()
                uris = snapshot.child("uri").getValue(String::class.java).toString()
                uid = snapshot.child("uid").getValue(String::class.java).toString()
                age = snapshot.child("age").getValue(String::class.java).toString()
                cartype = snapshot.child("carType").getValue(String::class.java).toString()
                description = snapshot.child("description").getValue(String::class.java).toString()
                gender = snapshot.child("gender").getValue(String::class.java).toString()
                phone = snapshot.child("phone").getValue(String::class.java).toString()
                location = snapshot.child("location").getValue(String::class.java).toString()
                pricePerLesson = snapshot.child("pricePerLesson").getValue(String::class.java).toString()
                transmission = snapshot.child("transmission").getValue(String::class.java).toString()

                binding.etCreateEmail.text = snapshot.child("email").getValue(String::class.java)
                binding.etCreateName.setText(snapshot.child("name").getValue(String::class.java))
                binding.pnumber.setText(snapshot.child("phone").getValue(String::class.java))
                binding.gender.setText(snapshot.child("gender").getValue(String::class.java))
                binding.cartype.setText(snapshot.child("carType").getValue(String::class.java))
                binding.description.setText(snapshot.child("description").getValue(String::class.java))
                binding.price.setText(snapshot.child("pricePerLesson").getValue(String::class.java))
                binding.ttype.setText(snapshot.child("transmission").getValue(String::class.java))
                binding.age.setText(snapshot.child("age").getValue(String::class.java))
                binding.location.setText(snapshot.child("location").getValue(String::class.java))
                binding.description.setText(snapshot.child("description").getValue(String::class.java))
                Glide.with(binding.addImage).load(snapshot.child("uri").getValue(String::class.java)).into(binding.addImage)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructorProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateLearnerToDatabase() {
        if (replaceEmail != "") {
            dbLearner =
                FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Instructors").child("Users")
                    .child(replaceEmail.replace(".", "%"))

            name = binding.etCreateName.text.toString()
            gender = binding.gender.text.toString()
            email = binding.etCreateEmail.text.toString()
            location = binding.location.text.toString()
            phone = binding.pnumber.text.toString()
            pricePerLesson = binding.price.text.toString()
            transmission = binding.ttype.text.toString()
            cartype = binding.cartype.text.toString()
            description = binding.description.text.toString()
            if (selectedImage != null) {
                pdd = ProgressDialog(requireActivity())
                pdd!!.setTitle("Uploading Data.......")
                pdd!!.show()
                val randomise = UUID.randomUUID().toString()
                val ref: StorageReference = storageReference!!.child("image/$randomise")
                ref.putFile(selectedImage!!).addOnSuccessListener(OnSuccessListener<Any?> {
                    ref.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                        // Sign in success, update UI with the signed-in user's informatio
                        uris = uri.toString()
                        val instructor = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "uid" to uid,
                            "age" to age,
                            "carType" to cartype,
                            "description" to description,
                            "gender" to gender,
                            "location" to location,
                            "phone" to phone,
                            "pricePerLesson" to pricePerLesson,
                            "transmission" to transmission,
                            "uri" to uris
                        )
                        dbLearner.updateChildren(instructor as Map<String, Any>)
                        Toast.makeText(requireActivity(), "Profile Updated", Toast.LENGTH_SHORT).show()
                    })
                    pdd!!.dismiss()
                }).addOnFailureListener(OnFailureListener {
                    Toast.makeText(requireActivity(), "Failed!", Toast.LENGTH_SHORT).show()

                }).addOnProgressListener { snapshot ->
                    val progresspercent: Double =
                        100.00 * snapshot.bytesTransferred / snapshot.totalByteCount
                    pdd!!.setMessage("Percentage: " + progresspercent.toInt() + "%")
                }
            } else {
                val instructor = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "uid" to uid,
                    "age" to age,
                    "carType" to cartype,
                    "description" to description,
                    "gender" to gender,
                    "location" to location,
                    "phone" to phone,
                    "pricePerLesson" to pricePerLesson,
                    "transmission" to transmission,
                    "uri" to uris
                )
                dbLearner.updateChildren(instructor as Map<String, Any>)
                Toast.makeText(requireActivity(), "Profile Updated", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireActivity(), "Sign in required!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            selectedImage = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver, selectedImage
                )
                Toast.makeText(requireActivity(), "Image has been selected", Toast.LENGTH_SHORT)
                    .show()
                binding.addImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}