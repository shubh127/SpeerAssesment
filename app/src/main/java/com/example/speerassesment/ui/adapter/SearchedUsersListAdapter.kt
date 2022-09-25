package com.example.speerassesment.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speerassesment.data.model.User
import com.example.speerassesment.databinding.ItemSearchedUserBinding
import com.example.speerassesment.listener.UserProfileClickListener
import com.example.speerassesment.ui.viewholder.SearchedUserViewHolder


class SearchedUsersListAdapter(
    private var userList: List<User>,
    private val clickListener: UserProfileClickListener
) : RecyclerView.Adapter<SearchedUserViewHolder>() {

    private lateinit var binding: ItemSearchedUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedUserViewHolder {
        binding =
            ItemSearchedUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, clickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<User>) {
        this.userList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = userList.size
}