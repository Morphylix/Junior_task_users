package com.morphylix.android.junior_task_users.presentation

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
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
import com.morphylix.android.junior_task_users.data.model.domain.User

private const val TAG = "UserDetailsFragment"

class UserDetailsFragment : Fragment() {

    private lateinit var userNameTextview: TextView
    private lateinit var userAgeTextView: TextView
    private lateinit var userPhoneButton: TextView
    private lateinit var userEmailButton: TextView
    private lateinit var userEyeColorImageView: ImageView
    private lateinit var userFavouriteFruitImageView: ImageView
    private lateinit var userRegisterDateTextView: TextView
    private lateinit var userCoordinatesButton: TextView
    private lateinit var userFriendsRecyclerView: RecyclerView

    private val userDetailsViewModel: UserDetailsViewModel by activityViewModels()
    private val dateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy")

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_details, container, false)

        userNameTextview = view.findViewById(R.id.user_name_text_view)
        userAgeTextView = view.findViewById(R.id.user_age_text_view)
        userPhoneButton = view.findViewById(R.id.user_phone_button)
        userEmailButton = view.findViewById(R.id.user_email_button)
        userEyeColorImageView = view.findViewById(R.id.user_eye_image_view)
        userFavouriteFruitImageView = view.findViewById(R.id.user_favourite_fruit_image_view)
        userRegisterDateTextView = view.findViewById(R.id.user_register_date_text_view)
        userCoordinatesButton = view.findViewById(R.id.user_coordinates_button)

        userFriendsRecyclerView = view.findViewById(R.id.user_friend_list_recycler_view)
        userFriendsRecyclerView.layoutManager = LinearLayoutManager(context)

        userPhoneButton.setOnClickListener {
            startActivity(startPhoneIntent(it as Button))
        }

        userEmailButton.setOnClickListener {
            startActivity(startEmailIntent(it as Button))
        }

        userCoordinatesButton.setOnClickListener {
            startActivity(startGeoIntent(it as Button))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUserLiveDataPair = userDetailsViewModel.getUser(args.userId)
        val currentUserLiveData = currentUserLiveDataPair.first
        currentUserLiveData.observe(viewLifecycleOwner, Observer { currentUser ->
            if (currentUser != null) {
                updateUI(currentUser)
            }
        })

        val currentUserFriendsLiveData = currentUserLiveDataPair.second
        currentUserFriendsLiveData.observe(viewLifecycleOwner, Observer { friendsList ->
            userFriendsRecyclerView.adapter = UserFriendsAdapter(friendsList)
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
        userNameTextview.text = currentUser.name
        userAgeTextView.text = currentUser.age.toString()
        userAgeTextView.text = getString(R.string.user_age_string, currentUser.age)
        userPhoneButton.text = currentUser.phone
        userEmailButton.text = currentUser.email
        userRegisterDateTextView.text = getString(
            R.string.user_registered_string,
            dateFormat.format(currentUser.registerDate).toString()
        )
        userCoordinatesButton.text =
            currentUser.latitude.toString() + ", " + currentUser.longitude.toString()
        userFavouriteFruitImageView.setImageResource(currentUser.favoriteFruit.imgId)
        userEyeColorImageView.setImageResource(currentUser.eyeColor.imgId)
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