package com.example.speerassesment.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.speerassesment.data.model.User
import com.example.speerassesment.databinding.ItemSearchedUserBinding
import com.example.speerassesment.listener.UserProfileClickListener

class SearchedUserViewHolder(
    private val binding: ItemSearchedUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User, clickListener: UserProfileClickListener) {
        binding.userData = user
        binding.itemClick = clickListener
    }
}