package com.example.weatherapp.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.presentation.adapters.ViewPagerAdapter
import com.example.weatherapp.domain.models.WeatherItem
import com.example.weatherapp.presentation.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )

    private val tList = listOf(
        "Hours",
        "Days"
    )

    private var _binding: FragmentMainBinding?=null
    private val binding get()=_binding!!

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    private val viewModel:WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()


        viewModel.currentWeather.observe(viewLifecycleOwner){
            bindCurrentWeather(it)
        }

        if (!viewModel.loadSavedLocationWeatherData())
            getLocation()

    }



    private fun bindCurrentWeather(weather: WeatherItem){
        binding.apply {
            tvData.text = weather.time
            tvCondition.text=weather.condition
            tvCity.text = weather.city
            tvCurrentTemp.text=weather.currentTemp+"°C"
            val maxMinTemp=weather.maxTemp+"°C/"+weather.minTemp+"°C"
            tvMaxMinTemp.text=maxMinTemp
            imWeather.load("https:"+weather.imageUrl)
        }
    }

    private fun init() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){
                tab, pos -> tab.text = tList[pos]
        }.attach()

        ibSearch.setOnClickListener{
            if (!binding.editTextCity.text.isNullOrEmpty())
                viewModel.setCityAndUpdateData(binding.editTextCity.text.toString())
        }
        ibSync.setOnClickListener {
            getLocation()
        }
        ibClear.setOnClickListener {
            editTextCity.text=null
        }
    }



    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {

                        viewModel.setCoordinatesAndUpdateData(location.latitude,location.longitude)

                    }
                }
            } else {
                Toast.makeText(activity, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
