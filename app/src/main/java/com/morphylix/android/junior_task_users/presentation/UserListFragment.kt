package com.morphylix.android.junior_task_users.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.morphylix.android.junior_task_users.data.model.domain.User

private const val TAG = "UserListFragment"

class UserListFragment : Fragment() {

    private lateinit var userListRecyclerView: RecyclerView
    private val userListViewModel: UserListViewModel by activityViewModels()
    private lateinit var userListRecyclerViewAdapter: UserListAdapter
    private lateinit var userListSwipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        userListRecyclerView = view.findViewById(R.id.user_list_recycler_view)
        userListRecyclerView.layoutManager = LinearLayoutManager(context)
        userListRecyclerViewAdapter = userListViewModel.userListLiveData.value.let {
            if (it != null) {
                UserListAdapter(it)
            } else {
                UserListAdapter(listOf())
            }
        }
        userListRecyclerView.adapter = userListRecyclerViewAdapter
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

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var user: User

        private val userNameTextView: TextView = view.findViewById(R.id.user_name_text_view)
        private val userEmailTextView: TextView = view.findViewById(R.id.user_email_button)
        private val isActive: ImageView = view.findViewById(R.id.user_is_active_image_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(user: User) {
            this.user = user
            userNameTextView.text = user.name
            userEmailTextView.text = user.email
            if (user.isActive) {
                isActive.setImageResource(R.drawable.is_active_true)
            } else {
                isActive.setImageResource(R.drawable.is_active_false)
            }
        }

        override fun onClick(p0: View?) {
            if (user.isActive) {
                val userId = user.id
                val action = UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(userId)
                p0?.findNavController()?.navigate(action)
            }
        }
    }

    class UserListAdapter(private val userList: List<User>) :
        RecyclerView.Adapter<UserViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
            return UserViewHolder(view)
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
        userListRecyclerView.adapter = userListRecyclerViewAdapter
        Log.i(TAG, "UI was updated")
    }

    private fun refreshUI() {
        userListViewModel.getUserList()
    }
}