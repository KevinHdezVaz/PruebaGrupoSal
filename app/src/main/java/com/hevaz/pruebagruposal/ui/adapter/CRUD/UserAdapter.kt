package com.hevaz.pruebagruposal.ui.adapter.CRUD

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.databinding.ItemUserBinding
import com.hevaz.pruebagruposal.utils.UserDiffCallback
import com.squareup.picasso.Picasso
import java.util.Locale


class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    var onUserClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    fun getHeaderForPosition(position: Int): String {
        return getItem(position).first_name.substring(0, 1).toUpperCase()  // Esto devolvería la primera letra del nombre del usuario como encabezado.
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewName.text = "${user.first_name} ${user.last_name}"
            binding.textViewEmail.text = user.email
            Picasso.get()
                .load(user.avatar)
                .placeholder(R.drawable.placeholer)
                .into(binding.imageViewAvatar)

            itemView.setOnClickListener {
                onUserClicked?.invoke(user.id)
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}