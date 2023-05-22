package com.mosfeq.drivingfirstgithub.learner

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
import com.mosfeq.drivingfirstgithub.databinding.FragmentProfilePageBinding
import java.io.IOException
import java.util.UUID

class ProfilePage : Fragment(R.layout.fragment_profile_page) {

    lateinit var uid: String
    lateinit var email: String
    lateinit var uris: String
    lateinit var name: String
    lateinit var signInCheck: String
    lateinit var replaceEmail: String

    private lateinit var dbLearner: DatabaseReference

    var selectedImage: Uri? = null
    val PICK_IMAGE = 1
    var bitmap: Bitmap? = null
    var pdd: ProgressDialog? = null
    var storageReference: StorageReference? = null

    private lateinit var binding: FragmentProfilePageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        signInCheck = Preference.readString(requireActivity(), "email").toString()
        if(signInCheck!=""){
            replaceEmail = signInCheck
            storageReference =
                FirebaseStorage.getInstance("gs://driving-first-github.appspot.com/").reference
            if (signInCheck != "out") {
                replaceEmail.replace(".", "%")

                getUserData()
            }
        }

        binding.btnUpdate.setOnClickListener() {
            if (binding.tvEmail.text.trim().isNotEmpty()
            ) {
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
                .getReference("Learners").child("Users").child(replaceEmail.replace(".", "%"))

        dbLearner!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email = snapshot.child("email").getValue(String::class.java).toString()
                name = snapshot.child("name").getValue(String::class.java).toString()
                uris = snapshot.child("uri").getValue(String::class.java).toString()
                uid = snapshot.child("uid").getValue(String::class.java).toString()
                binding.tvEmail.text = snapshot.child("email").getValue(String::class.java)
                binding.etName.setText(snapshot.child("name").getValue(String::class.java))
                Glide.with(binding.addImage)
                    .load(snapshot.child("uri").getValue(String::class.java))
                    .into(binding.addImage)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun updateLearnerToDatabase() {
        if(replaceEmail!=""){
            dbLearner =
                FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Learners").child("Users").child(replaceEmail.replace(".", "%"))
            name = binding.etName.text.toString()
            if (selectedImage != null) {
                pdd = ProgressDialog(requireActivity())
                pdd!!.setTitle("Uploading Data.......")
                pdd!!.show()
                val randomise = UUID.randomUUID().toString()
                val ref: StorageReference = storageReference!!.child("image/$randomise")
                Log.i("androidstudio", "addLearnerToDatabase: $ref\n$selectedImage")
                ref.putFile(selectedImage!!).addOnSuccessListener(OnSuccessListener<Any?> {
                    ref.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                        uris = uri.toString()
                        val learner = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "uid" to uid,
                            "uri" to uris
                        )
                        dbLearner.updateChildren(learner as Map<String, Any>)
                        Toast.makeText(requireActivity(), "updated!", Toast.LENGTH_SHORT).show()
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
                val learner = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "uid" to uid,
                    "uri" to uris
                )
                dbLearner.updateChildren(learner as Map<String, Any>)
            }
            val action = ProfilePageDirections.actionProfilePageToSettingsLearnerFragment()
            findNavController().navigate(action)
        }
        else{
            Toast.makeText(requireActivity(), "Sign in required!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            selectedImage = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    selectedImage
                )
                Toast.makeText(requireActivity(), "Image has been selected", Toast.LENGTH_SHORT)
                    .show()
                binding.addImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

}
