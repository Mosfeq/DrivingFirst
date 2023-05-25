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

class SettingsLearnerFragment: Fragment(R.layout.fragment_settings_learner) {

    lateinit var btnLogout: Button
    lateinit var btnUpdate: Button
    lateinit var btnTermsOfService: Button
    lateinit var btnPrivacyPolicy: Button
    lateinit var btnFAQ: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        btnUpdate = view.findViewById(R.id.updateProfile)
        btnLogout = view.findViewById(R.id.logout)
        btnTermsOfService = view.findViewById(R.id.termsOfService)
        btnPrivacyPolicy = view.findViewById(R.id.privacyPolicy)
        btnFAQ = view.findViewById(R.id.faq)

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

        btnTermsOfService.setOnClickListener {
            val action = SettingsLearnerFragmentDirections.actionSettingsLearnerFragmentToTermsOfServiceFragment()
            findNavController().navigate(action)
        }

        btnPrivacyPolicy.setOnClickListener {
            val action = SettingsLearnerFragmentDirections.actionSettingsLearnerFragmentToPrivacyPolicyFragment()
            findNavController().navigate(action)
        }

        btnFAQ.setOnClickListener {
            val action = SettingsLearnerFragmentDirections.actionSettingsLearnerFragmentToFaqFragment()
            findNavController().navigate(action)
        }

    }
}