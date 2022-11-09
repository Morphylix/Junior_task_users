package com.morphylix.android.junior_task_users.presentation.user_details

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morphylix.android.junior_task_users.domain.model.domain.User
import com.morphylix.android.junior_task_users.presentation.R
import com.morphylix.android.junior_task_users.presentation.databinding.FragmentUserDetailsBinding

private const val TAG = "UserDetailsFragment"

class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val userDetailsViewModel: UserDetailsViewModel by activityViewModels()

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat("HH:mm dd.MM.yyyy")
    } else {
        TODO("VERSION.SDK_INT < N")
    }

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.userFriendListRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.userPhoneButton.setOnClickListener {
            startActivity(startPhoneIntent(it as Button))
        }

        binding.userEmailButton.setOnClickListener {
            startActivity(startEmailIntent(it as Button))
        }

        binding.userCoordinatesButton.setOnClickListener {
            startActivity(startGeoIntent(it as Button))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUserLiveDataPair = userDetailsViewModel.getUser(args.userId)
        val currentUserLiveData = currentUserLiveDataPair.first
        currentUserLiveData.observe(viewLifecycleOwner) { currentUser ->
            if (currentUser != null) {
                updateUI(currentUser)
            }
        }

        val currentUserFriendsLiveData = currentUserLiveDataPair.second
        currentUserFriendsLiveData.observe(viewLifecycleOwner, Observer { friendsList ->
            binding.userFriendListRecyclerView.adapter = UserFriendsAdapter(friendsList)
        })
    }

    class UserFriendsViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
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
                val action = UserDetailsFragmentDirections.actionUserDetailsFragmentSelf(userId)
                p0?.findNavController()?.navigate(action)
            }
        }
    }

    class UserFriendsAdapter(private val friends: List<User>) :
        RecyclerView.Adapter<UserFriendsViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFriendsViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
            return UserFriendsViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserFriendsViewHolder, position: Int) {
            val friend = friends[position]
            holder.bind(friend)
        }

        override fun getItemCount(): Int {
            return friends.size
        }

    }

    private fun updateUI(currentUser: User) {
        with(binding) {
            userNameTextView.text = currentUser.name
            userAgeTextView.text = currentUser.age.toString()
            userAgeTextView.text = getString(R.string.user_age_string, currentUser.age)
            userPhoneButton.text = currentUser.phone
            userEmailButton.text = currentUser.email
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                userRegisterDateTextView.text = getString(
                    R.string.user_registered_string,
                    dateFormat.format(currentUser.registerDate).toString()
                )
            }
            userCoordinatesButton.text = getString(
                R.string.user_coordinates_string,
                currentUser.latitude,
                currentUser.longitude
            )
            userFavouriteFruitImageView.setImageResource(currentUser.favoriteFruit.imgId)
            userEyeImageView.setImageResource(currentUser.eyeColor.imgId)
        }
    }


    private fun startPhoneIntent(phoneButton: Button): Intent {
        val phoneUri: Uri = Uri.parse("tel:" + phoneButton.text)
        return Intent(Intent.ACTION_DIAL, phoneUri)
    }

    private fun startEmailIntent(emailButton: Button): Intent {
        val emailUri: Uri = Uri.parse("mailto:" + emailButton.text)
        val intent = Intent(Intent.ACTION_SENDTO, emailUri)
        return intent
    }

    private fun startGeoIntent(geoButton: Button): Intent {
        val mapUri = Uri.parse("geo:${geoButton.text}")
        return Intent(Intent.ACTION_VIEW, mapUri)
    }
}