package com.morphylix.android.junior_task_users.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.morphylix.android.junior_task_users.data.model.domain.User
import com.morphylix.android.junior_task_users.presentation.databinding.FragmentUserListBinding
import com.morphylix.android.junior_task_users.presentation.databinding.UserListItemBinding

private const val TAG = "UserListFragment"

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val userListViewModel: UserListViewModel by activityViewModels()
    private lateinit var userListRecyclerViewAdapter: UserListAdapter
    private lateinit var userListSwipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.userListRecyclerView.layoutManager = LinearLayoutManager(context)
        userListRecyclerViewAdapter = userListViewModel.userListLiveData.value.let {
            if (it != null) {
                UserListAdapter(it)
            } else {
                UserListAdapter(listOf())
            }
        }
        binding.userListRecyclerView.adapter = userListRecyclerViewAdapter
        userListSwipeRefreshLayout = view.findViewById(R.id.user_list_swipe_refresh_layout)
        userListSwipeRefreshLayout.setOnRefreshListener {
            refreshUI()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListViewModel.getUserList()
        userListViewModel.userListLiveData.observe(viewLifecycleOwner) { userList ->
            userList?.let { currentUserList ->
                Log.i(TAG, "Got ${currentUserList.size} users")
                updateUI(currentUserList)
                userListSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    inner class UserViewHolder(private val binding: UserListItemBinding) :
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

    inner class UserListAdapter(private val userList: List<User>) :
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

    private fun updateUI(userList: List<User>) {
        userListRecyclerViewAdapter = UserListAdapter(userList)
        binding.userListRecyclerView.adapter = userListRecyclerViewAdapter
        Log.i(TAG, "UI was updated")
    }

    private fun refreshUI() {
        userListViewModel.getUserList()
    }
}