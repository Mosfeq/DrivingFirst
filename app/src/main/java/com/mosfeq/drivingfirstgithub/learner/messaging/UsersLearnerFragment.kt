package com.mosfeq.drivingfirstgithub.learner.messaging

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup
import com.mosfeq.drivingfirstgithub.databinding.FragmentUsersLearnerBinding
import com.mosfeq.drivingfirstgithub.learner.adapters.UserGroupAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UsersLearnerFragment: Fragment() {

    var rep: String = ""
    private lateinit var userList: ArrayList<UserGroup>
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentUsersLearnerBinding

    private val userGroupAdapter: UserGroupAdapter = UserGroupAdapter()
    private val userGroupViewModel: UserGroupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersLearnerBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            findNavController().navigate(R.id.action_usersLearnerFragment_to_searchInstructorFragment)
        }
    }
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            groupsRecyclerView.adapter = userGroupAdapter
            groupsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        userList = arrayListOf<UserGroup>()

        rep = Preference.readString(requireActivity(), "email").toString()
        val a = rep.replace(".", "%")
        userGroupViewModel.groups.observe(viewLifecycleOwner) {
            userList.clear()
            if (it != null) {
                for (data in it) {
                    if (data.lemail.equals(rep)) {
                        Log.i("androidstudio", "data: " + data.lemail + "\n" + rep)
                        userList.add(data)
                        userGroupAdapter.submitList(userList)
                    }
                }
            }
        }

        userGroupAdapter.onClickHandler = {
            val bundle = Bundle().apply {
                putParcelable("group", it)
                putString("labelName", it.name)
            }
            findNavController().navigate(R.id.action_usersLearnerFragment_to_chatFragment, bundle)
        }

    }
}