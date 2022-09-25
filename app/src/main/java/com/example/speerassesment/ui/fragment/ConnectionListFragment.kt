package com.example.speerassesment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speerassesment.R
import com.example.speerassesment.databinding.FragmentConnectionListBinding
import com.example.speerassesment.helper.Constants
import com.example.speerassesment.helper.Constants.Companion.IS_FOLLOWING
import com.example.speerassesment.listener.UserProfileClickListener
import com.example.speerassesment.ui.adapter.SearchedUsersListAdapter
import com.example.speerassesment.viewmodel.ConnectionListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectionListFragment : Fragment(), UserProfileClickListener {
    private lateinit var binding: FragmentConnectionListBinding
    private val viewModel: ConnectionListViewModel by viewModels()
    private var userName: String = ""
    private var isFollowing: Boolean = false
    private lateinit var mAdapter: SearchedUsersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFollowing = it.getBoolean(IS_FOLLOWING, false)
            userName = it.getString(Constants.SELECTED_USER_NAME, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViews()
        setUpListeners()
    }

    private fun setUpListeners() {
        viewModel.getIsApiSuccessful().observe(viewLifecycleOwner) {
            if (it) {
                showHideItems(true)
            } else {
                showHideItems(false, getString(R.string.something_went_wrong))
            }
        }

        viewModel.getConnectionsListData().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                showHideItems(false, getString(R.string.no_connections))
            } else {
                showHideItems(true)
                binding.rvConnectionsList.visibility = View.VISIBLE
//                mAdapter.updateData(it)
            }
        }
    }

    private fun configViews() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = if (isFollowing) {
            getString(R.string.following)
        } else {
            getString(R.string.followers)
        }
//        mAdapter = SearchedUsersListAdapter(mutableListOf(), this)

        binding.rvConnectionsList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.showProgress = true
        viewModel.getConnectionList(userName, isFollowing)
    }

    private fun showHideItems(isShow: Boolean, msg: String = "") {
        binding.errorMsg = msg
        binding.showProgress = false
        binding.showList = isShow
    }

    override fun onProfileClick(userName: String) {
        val bundle = Bundle().apply {
            putString(Constants.SELECTED_USER_NAME, userName)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_connectionListFragment_to_userDetailsFragment, bundle)
    }
}