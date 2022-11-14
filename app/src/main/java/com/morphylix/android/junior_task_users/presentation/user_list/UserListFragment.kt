package com.morphylix.android.junior_task_users.presentation.user_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.morphylix.android.junior_task_users.domain.model.domain.User
import com.morphylix.android.junior_task_users.presentation.R
import com.morphylix.android.junior_task_users.presentation.databinding.FragmentUserListBinding
import com.morphylix.android.junior_task_users.presentation.databinding.UserListItemBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "UserListFragment"

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
        userListRecyclerViewAdapter = UserListAdapter(listOf())

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



    private fun updateUI(userList: List<User>) {
        userListRecyclerViewAdapter = UserListAdapter(userList)
        binding.userListRecyclerView.adapter = userListRecyclerViewAdapter
        Log.i(TAG, "UI was updated")
    }

    private fun refreshUI() {
        userListViewModel.getUserList()
    }
}