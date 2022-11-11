package com.morphylix.android.junior_task_users.presentation.user_details

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        provideListeners()


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
        currentUserFriendsLiveData.observe(viewLifecycleOwner) { friendsList ->
            binding.userFriendListRecyclerView.adapter = UserFriendsAdapter(friendsList)
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

    private fun provideListeners() {
        binding.userPhoneButton.setOnClickListener {
            startActivity(startPhoneIntent(it as Button))
        }

        binding.userEmailButton.setOnClickListener {
            startActivity(startEmailIntent(it as Button))
        }

        binding.userCoordinatesButton.setOnClickListener {
            startActivity(startGeoIntent(it as Button))
        }
    }
}