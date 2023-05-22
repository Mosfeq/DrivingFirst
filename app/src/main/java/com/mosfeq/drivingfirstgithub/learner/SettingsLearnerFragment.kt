package com.mosfeq.drivingfirstgithub.learner

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.FragmentSettingsLearnerBinding

class SettingsLearnerFragment: Fragment(R.layout.fragment_settings_learner) {

    lateinit var btnLogout: Button
    lateinit var btnUpdate: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        btnUpdate = view.findViewById(R.id.updateProfile)
        btnLogout = view.findViewById(R.id.logout)

        btnLogout.setOnClickListener() {
            Preference.writeString(requireActivity(), "email", "")
            Preference.writeString(requireActivity(), "role", "")
            Toast.makeText(context, "Logged out!", Toast.LENGTH_SHORT).show()
            requireActivity().finishAffinity()
        }

        btnUpdate.setOnClickListener() {
            val action = SettingsLearnerFragmentDirections.actionSettingsLearnerFragmentToProfilePage()
            findNavController().navigate(action)
        }
    }
}