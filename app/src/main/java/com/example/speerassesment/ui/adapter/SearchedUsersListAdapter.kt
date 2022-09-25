package com.example.speerassesment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.speerassesment.data.model.User
import com.example.speerassesment.databinding.ItemSearchedUserBinding
import com.example.speerassesment.listener.UserProfileClickListener
import com.example.speerassesment.ui.viewholder.SearchedUserViewHolder

//adapter for users list (searched, followers, followings)
class SearchedUsersListAdapter(private val clickListener: UserProfileClickListener) :
    PagingDataAdapter<User, SearchedUserViewHolder>(SEARCH_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedUserViewHolder {
        val binding =
            ItemSearchedUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {
        val search = getItem(position)
        if (search != null) {
            holder.bind(search, clickListener)
        }
    }

    companion object {
        private val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }
}