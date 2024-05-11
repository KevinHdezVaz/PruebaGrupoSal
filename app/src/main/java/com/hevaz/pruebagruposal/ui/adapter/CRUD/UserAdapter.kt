package com.hevaz.pruebagruposal.ui.adapter.CRUD

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.databinding.ItemUserBinding
import com.hevaz.pruebagruposal.utils.UserDiffCallback
import com.squareup.picasso.Picasso
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()), Filterable {
    var onUserClicked: ((Int) -> Unit)? = null
    var onEdit: ((User) -> Unit)? = null
    var onDelete: ((User) -> Unit)? = null

    private var userList: List<User> = listOf()

    init {
        userList = currentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewName.text = "${user.first_name} ${user.last_name}"
            binding.textViewEmail.text = user.email
            Picasso.get().load(user.avatar).placeholder(R.drawable.placeholer).into(binding.imageViewAvatar)
            itemView.setOnClickListener {
                onUserClicked?.invoke(user.id)
            }
        }
    }

    override fun submitList(list: List<User>?) {
        super.submitList(list?.let { ArrayList(it) })
        list?.also { userList = it }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    userList
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    userList.filter {
                        it.first_name.toLowerCase().contains(filterPattern) ||
                                it.last_name.toLowerCase().contains(filterPattern)
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.values?.let {
                    @Suppress("UNCHECKED_CAST")
                    submitList(it as List<User>)
                }
            }
        }
    }
    fun getHeaderForPosition(position: Int): String {
        return getItem(position).first_name.substring(0, 1).toUpperCase()
    }
    val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
            return 0.1f
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.RIGHT) {
                onEdit?.invoke(getItem(position))
            } else if (direction == ItemTouchHelper.LEFT) {
                onDelete?.invoke(getItem(position))
            }
        }

        override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            RecyclerViewSwipeDecorator.Builder(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.red))
                .addSwipeLeftActionIcon(R.drawable.borasx)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.colorTRes))
                .addSwipeRightActionIcon(R.drawable.editaar)
                .addSwipeLeftLabel("Delete")
                .addSwipeRightLabel("Edit")
                .setSwipeLeftLabelColor(Color.WHITE)
                .setSwipeRightLabelColor(Color.WHITE)
                .create()
                .decorate()
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}
