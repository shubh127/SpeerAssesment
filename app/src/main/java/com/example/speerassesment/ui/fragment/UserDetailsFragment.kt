package com.example.speerassesment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.speerassesment.R
import com.example.speerassesment.databinding.FragmentUserDetailsBinding
import com.example.speerassesment.helper.Constants.Companion.IS_FOLLOWING
import com.example.speerassesment.helper.Constants.Companion.SELECTED_USER_NAME
import com.example.speerassesment.listener.ConnectionsClickListener
import com.example.speerassesment.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(), ConnectionsClickListener {
    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UserDetailViewModel by viewModels()
    private var selectedUserName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedUserName = it.getString(SELECTED_USER_NAME, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViews()
        setUpListeners()
    }

    private fun configViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = selectedUserName

        binding.itemClick = this
        binding.showProgress = true

        //api call
        viewModel.getUserDetail(selectedUserName)
    }

    private fun setUpListeners() {
        //observing api status
        viewModel.getIsDetailSuccessful().observe(viewLifecycleOwner) {
            binding.showProgress = false
        }

        //observing data from api
        viewModel.getUserDetailData().observe(viewLifecycleOwner) {
            binding.userDetails = it
        }
    }

    //handled on click of followers/followings
    override fun onConnectionsClick(isFollowingClicked: Boolean) {
        val bundle = Bundle().apply {
            putBoolean(IS_FOLLOWING, isFollowingClicked)
            putString(SELECTED_USER_NAME, selectedUserName)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_userDetailsFragment_to_connectionListFragment, bundle)
    }
}