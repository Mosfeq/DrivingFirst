package com.mosfeq.drivingfirstgithub.instructor

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

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
            val action = SettingsFragmentDirections.actionSettingsFragmentToInstructorProfilePage()
            findNavController().navigate(action)
        }

        btnTermsOfService.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToTermsOfServiceFragment2()
            findNavController().navigate(action)
        }

        btnPrivacyPolicy.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToPrivacyPolicyFragment2()
            findNavController().navigate(action)
        }

        btnFAQ.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToFaqFragment2()
            findNavController().navigate(action)
        }
    }
}