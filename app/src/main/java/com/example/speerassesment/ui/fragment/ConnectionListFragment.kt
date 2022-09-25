package com.example.speerassesment.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speerassesment.R
import com.example.speerassesment.databinding.FragmentConnectionListBinding
import com.example.speerassesment.helper.Constants
import com.example.speerassesment.helper.Constants.Companion.IS_FOLLOWING
import com.example.speerassesment.listener.UserProfileClickListener
import com.example.speerassesment.ui.adapter.SearchRepoStateAdapter
import com.example.speerassesment.ui.adapter.SearchedUsersListAdapter
import com.example.speerassesment.viewmodel.ConnectionListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectionListFragment : Fragment(), UserProfileClickListener {
    private lateinit var binding: FragmentConnectionListBinding
    private val viewModel: ConnectionListViewModel by viewModels()
    private var userName: String = ""
    private var isFollowing: Boolean = false
    private var mAdapter = SearchedUsersListAdapter(this)

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

    @SuppressLint("ClickableViewAccessibility")
    private fun configViews() {
        //handling toolbar title
        (requireActivity() as AppCompatActivity).supportActionBar?.title = if (isFollowing) {
            getString(R.string.following)
        } else {
            getString(R.string.followers)
        }

        //setting recycler view and adapter properties
        binding.rvConnectionsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            setOnTouchListener { _, motionEvent ->
                binding.rvConnectionsList.onTouchEvent(motionEvent)
                true
            }

            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = SearchRepoStateAdapter { mAdapter.retry() },
                footer = SearchRepoStateAdapter { mAdapter.retry() }
            )
        }

        //api call
        viewModel.getData(userName, isFollowing)
    }

    private fun setUpListeners() {
        //observing live paging data
        viewModel.connectionsResponse.observe(viewLifecycleOwner) {
            mAdapter.submitData(lifecycle, it)
        }

        //observing load state to handle visibility of views
        mAdapter.addLoadStateListener { loadState ->
            binding.apply {
                showProgress = loadState.source.refresh is LoadState.Loading
                showList = loadState.source.refresh is LoadState.NotLoading
                showError = loadState.source.refresh is LoadState.Error
            }
        }
    }

    //handled on click of item
    override fun onProfileClick(userName: String) {
        val bundle = Bundle().apply {
            putString(Constants.SELECTED_USER_NAME, userName)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_connectionListFragment_to_userDetailsFragment, bundle)
    }
}