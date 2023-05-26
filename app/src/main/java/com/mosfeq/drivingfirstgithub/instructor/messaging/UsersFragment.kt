package com.mosfeq.drivingfirstgithub.instructor.messaging

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
import com.mosfeq.drivingfirstgithub.databinding.FragmentUsersBinding
import com.mosfeq.drivingfirstgithub.instructor.adapter.UserGroupInstAdapter
import com.mosfeq.drivingfirstgithub.learner.messaging.UserGroupViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UsersFragment : Fragment(){

    var rep: String = ""
    private lateinit var userList: ArrayList<UserGroup>

    private lateinit var binding: FragmentUsersBinding
    private lateinit var auth: FirebaseAuth

    private val userGroupInstAdapter: UserGroupInstAdapter = UserGroupInstAdapter()
    private val userGroupViewModel: UserGroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentUsersBinding.inflate(inflater)
        auth= FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply{
            groupsRecyclerView.adapter = userGroupInstAdapter
            groupsRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        }
        userList = arrayListOf<UserGroup>()

        rep = Preference.readString(requireActivity(), "email").toString()
        val a = rep.replace(".", "%")
        userGroupViewModel.groups.observe(viewLifecycleOwner) {
            userList.clear()
            if (it != null) {
                for(data in it){
                    Log.i("androidstudio", "data: "+data.iemail+"\n"+a)

                    if(data.iemail.equals(a)){
                        Log.i("androidstudio", "data: "+data.iemail+"\n"+a)
                        userList.add(data)
                        userGroupInstAdapter.submitList(userList)
                    }
                }
            }
        }

        userGroupInstAdapter.onClickHandler={
            val bundle=Bundle().apply {
                putParcelable("group",it)
            }
            findNavController().navigate(R.id.action_usersFragment_to_chatFragment2, bundle)
        }

    }

}