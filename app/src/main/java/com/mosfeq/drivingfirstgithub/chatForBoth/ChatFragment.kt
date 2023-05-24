package com.mosfeq.drivingfirstgithub.chatForBoth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.chatForBoth.adapters.ChatAdapter
import com.mosfeq.drivingfirstgithub.databinding.FragmentChatBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ChatFragment: Fragment(R.layout.fragment_chat) {

    private lateinit var viewModel: ChatViewModel
    private lateinit var viewModelFactory: ChatViewModelFactory
    private val chatAdapter: ChatAdapter = ChatAdapter()

    private lateinit var binding: FragmentChatBinding
    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentChatBinding.inflate(inflater)

        args.group?.groupId?.let {
            viewModelFactory = ChatViewModelFactory(it)
        }

        viewModel= ViewModelProvider(this,viewModelFactory)[ChatViewModel::class.java]
        return binding.root
    }
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.chatRecyclerView.apply {
            adapter = chatAdapter
            layoutManager= LinearLayoutManager(requireContext())
        }

        viewModel.loadInitialChat.observe(viewLifecycleOwner){
            chatAdapter.submitList(it)
        }
        //This above observe detects changes after the below observe

        viewModel.chats.observe(viewLifecycleOwner){
//            Log.d(TAG, "onViewCreated: $it")
            chatAdapter.submitList(it)
            binding.chatRecyclerView.scrollToPosition(chatAdapter.itemCount-1)
            //If there is change to chat, automatically scrolls to last chat item
        }

        binding.sendMessageButton.setOnClickListener {
            val messageText= binding.sendMessageText.text.toString()
            binding.sendMessageText.setText("")
            viewModel.putData(messageText)
        }

    }
}