package com.example.speerassesment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speerassesment.R
import com.example.speerassesment.databinding.SearchFragmentBinding
import com.example.speerassesment.helper.Constants.Companion.SELECTED_USER_NAME
import com.example.speerassesment.listener.UserProfileClickListener
import com.example.speerassesment.ui.adapter.SearchedUsersListAdapter
import com.example.speerassesment.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), UserProfileClickListener {

    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var mAdapter: SearchedUsersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViews()
        setUpListeners()
    }

    private fun configViews() {
        binding.showList = false
        binding.showProgress = false
        binding.errorMsg = getString(R.string.search_hint)

        mAdapter = SearchedUsersListAdapter(mutableListOf(), this)

        binding.rvSearchedProfiles.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setUpListeners() {
        viewModel.getIsSearchSuccessful().observe(viewLifecycleOwner) {
            if (it) {
                showHideItems(true)
            } else {
                showHideItems(false, getString(R.string.something_went_wrong))
            }
        }

        viewModel.getUserProfilesList().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                showHideItems(false, getString(R.string.no_user_profiles))
            } else {
                showHideItems(true)
                mAdapter.updateData(it)
            }
        }

        binding.svSearch.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                handleOnSearch(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }

    private fun handleOnSearch(input: String) {
        if (input.length < 3) {
            Toast.makeText(
                context,
                R.string.search_error_1,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            binding.showProgress = true
            binding.rvSearchedProfiles.scrollToPosition(0)
            viewModel.searchUsers(input)
            binding.svSearch.clearFocus()
        }
    }

    private fun showHideItems(isShow: Boolean, msg: String = "") {
        binding.errorMsg = msg
        binding.showProgress = false
        binding.showList = isShow
    }

    override fun onProfileClick(userName: String) {
        val bundle = Bundle().apply {
            putString(SELECTED_USER_NAME, userName)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_searchFragment_to_userDetailsFragment, bundle)
    }
}