package com.mosfeq.drivingfirstgithub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mosfeq.drivingfirstgithub.databinding.FragmentTermsOfServiceBinding

class TermsOfServiceFragment: Fragment() {

    private lateinit var binding: FragmentTermsOfServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentTermsOfServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

}