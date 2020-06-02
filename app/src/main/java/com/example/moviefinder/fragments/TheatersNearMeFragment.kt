package com.example.moviefinder.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.R
import com.example.moviefinder.adapters.TheaterListAdapter
import com.example.moviefinder.databinding.FragmentTheatersNearMeBinding
import com.example.moviefinder.utils.Constants
import com.example.moviefinder.view_models.MovieViewModel
import com.google.android.gms.location.*
import timber.log.Timber

class TheatersNearMeFragment : Fragment() {

    private val mMovieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationProviderClient: FusedLocationProviderClient
    private lateinit var mTheaterListAdapter: TheaterListAdapter
    private var mUserOrigin = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mTheaterListAdapter = TheaterListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTheatersNearMeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theaters_near_me, container, false)

        val theaterListRecyclerView = binding.rvTheaterList
        theaterListRecyclerView.layoutManager = LinearLayoutManager(context)
        theaterListRecyclerView.adapter = mTheaterListAdapter

        val zipCode = binding.etZipCode

        binding.lifecycleOwner = this

        binding.btnSearchForTheaters.setOnClickListener {

            mUserOrigin = zipCode.text.toString()

            if (mUserOrigin.isEmpty()) {
                Toast.makeText(context, "Enter a valid US ZIP Code", Toast.LENGTH_LONG).show()
            } else {
                mMovieViewModel.callTheaterSearchAPI(mUserOrigin)
            }
        }

        binding.btnMyLocation.setOnClickListener {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.GPS_PERMISSION_REQUEST_CODE
            )
        }

        mMovieViewModel.mTheaterSearchResults.observe(viewLifecycleOwner, Observer {
            mTheaterListAdapter.mTheaterList = it
        })

        return binding.root


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            Constants.GPS_PERMISSION_REQUEST_CODE ->

                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mLocationProviderClient.lastLocation.addOnSuccessListener { deviceLocation ->

                        if (deviceLocation == null) {

                            Toast.makeText(
                                context,
                                "Make sure GPS is on and try again.",
                                Toast.LENGTH_LONG
                            ).show()

                            requestUserLocationUpdate()

                        } else {
                            Timber.i("LOCATION: " + deviceLocation.latitude + " " + deviceLocation.longitude)
                            mUserOrigin =
                                deviceLocation.latitude.toString() + "," + deviceLocation.longitude.toString()
                            mMovieViewModel.callTheaterSearchAPI(mUserOrigin)
                        }
                    }

                } else
                    Toast.makeText(context, "GPS Permission is required", Toast.LENGTH_LONG).show()
        }
    }

    fun requestUserLocationUpdate() {

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    mUserOrigin = location.latitude.toString() + "," + location.longitude.toString()
                    mMovieViewModel.callTheaterSearchAPI(mUserOrigin)
                    mLocationProviderClient.removeLocationUpdates(this)
                }
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        mLocationProviderClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }


}

