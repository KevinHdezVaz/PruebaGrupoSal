package com.hevaz.pruebagruposal.utils

import androidx.recyclerview.widget.DiffUtil
import com.hevaz.pruebagruposal.data.model.CRUD.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
