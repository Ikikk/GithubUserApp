package com.ikik.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.databinding.UserRowBinding
import com.ikik.githubuserapp.helper.UserDiffCallback

class UserAdapter(private var users: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    var onItemClick: ((String) -> Unit)? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(users[position].toString())
                }
            }
        }

        fun bind(user: ItemsItem) {
            Glide.with(binding.imgAvatar.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)
            binding.tvUsername.text = user.login
            binding.tvUrl.text = user.htmlUrl
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int){
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount() : Int = users.size

    fun updateUsers(newUser: List<ItemsItem>) {
        val diffCallback = UserDiffCallback(this.users, newUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.users = newUser
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}