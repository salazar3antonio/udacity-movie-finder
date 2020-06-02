package com.example.moviefinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.moviefinder.R
import com.example.moviefinder.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentHomeScreenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)

        binding.btnNowPlaying.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeScreenFragment_to_nowPlayingFragment))
        binding.btnTheatersNear.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeScreenFragment_to_theatersNearMeFragment))

        return binding.root
    }
}