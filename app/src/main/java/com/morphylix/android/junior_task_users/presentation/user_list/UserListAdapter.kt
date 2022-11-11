package com.morphylix.android.junior_task_users.presentation.user_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.morphylix.android.junior_task_users.domain.model.domain.User
import com.morphylix.android.junior_task_users.presentation.R
import com.morphylix.android.junior_task_users.presentation.databinding.UserListItemBinding

class UserViewHolder(private val binding: UserListItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private lateinit var user: User


    init {
        itemView.setOnClickListener(this)
    }

    fun bind(user: User) {
        this.user = user
        with(binding) {
            userNameTextView.text = user.name
            userEmailButton.text = user.email
            if (user.isActive) {
                userIsActiveImageView.setImageResource(R.drawable.is_active_true)
            } else {
                userIsActiveImageView.setImageResource(R.drawable.is_active_false)
            }
        }
    }

    override fun onClick(p0: View?) {
        if (user.isActive) {
            val userId = user.id
            val action =
                UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(userId)
            p0?.findNavController()?.navigate(action)
        }
    }
}

class UserListAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
        Log.i(TAG, "Binded list successfully")
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}