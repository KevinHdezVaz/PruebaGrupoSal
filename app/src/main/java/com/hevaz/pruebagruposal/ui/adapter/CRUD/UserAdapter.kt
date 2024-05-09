package com.hevaz.pruebagruposal.ui.adapter.CRUD

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
 import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.User
 import com.hevaz.pruebagruposal.databinding.ItemUserBinding
import com.hevaz.pruebagruposal.utils.UserDiffCallback
import com.squareup.picasso.Picasso

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.textViewName).text = "${user.first_name} ${user.last_name}"
            itemView.findViewById<TextView>(R.id.textViewEmail).text = user.email
            val avatarImageView = itemView.findViewById<ImageView>(R.id.imageViewAvatar)

             Picasso.get()
                .load(user.avatar)
                .placeholder(R.drawable.placeholer)  // Puedes definir una imagen de placeholder
                 .into(avatarImageView)

        }
    }
}
