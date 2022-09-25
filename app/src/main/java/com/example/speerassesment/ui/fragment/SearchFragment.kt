package com.example.speerassesment.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speerassesment.R
import com.example.speerassesment.databinding.SearchFragmentBinding
import com.example.speerassesment.helper.Constants.Companion.SELECTED_USER_NAME
import com.example.speerassesment.listener.UserProfileClickListener
import com.example.speerassesment.ui.adapter.SearchRepoStateAdapter
import com.example.speerassesment.ui.adapter.SearchedUsersListAdapter
import com.example.speerassesment.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), UserProfileClickListener {

    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModels()
    private var mAdapter = SearchedUsersListAdapter(this)

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

    @SuppressLint("ClickableViewAccessibility")
    private fun configViews() {

        binding.rvSearchedProfiles.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            setOnTouchListener { _, motionEvent ->
                binding.rvSearchedProfiles.onTouchEvent(motionEvent)
                true
            }

            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = SearchRepoStateAdapter { mAdapter.retry() },
                footer = SearchRepoStateAdapter { mAdapter.retry() }
            )
        }
    }

    private fun setUpListeners() {

        viewModel.searchResponse.observe(viewLifecycleOwner) {
            mAdapter.submitData(lifecycle, it)
        }

        binding.svSearch.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                binding.rvSearchedProfiles.scrollToPosition(0)
                viewModel.search(s)
                binding.svSearch.clearFocus()

                if (s.isBlank()) {
                    binding.tvErrorMsg.text = getString(R.string.search_hint)
                } else {
                    binding.tvErrorMsg.text = getString(R.string.something_went_wrong)
                }
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        mAdapter.addLoadStateListener { loadState ->
            binding.apply {
                showProgress = loadState.source.refresh is LoadState.Loading
                showList = loadState.source.refresh is LoadState.NotLoading
                showError = loadState.source.refresh is LoadState.Error
            }
        }
    }

    override fun onProfileClick(userName: String) {
        val bundle = Bundle().apply {
            putString(SELECTED_USER_NAME, userName)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_searchFragment_to_userDetailsFragment, bundle)
    }
}