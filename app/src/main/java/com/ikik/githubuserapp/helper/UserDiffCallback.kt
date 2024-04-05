package com.ikik.githubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.ikik.githubuserapp.data.remote.response.ItemsItem

class UserDiffCallback(private val oldList: List<ItemsItem>, private val newList: List<ItemsItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}