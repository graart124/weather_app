package com.example.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.presentation.adapters.HourAdapter
import com.example.weatherapp.databinding.FragmentHoursBinding

import com.example.weatherapp.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: HourAdapter

    private val viewModel:WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding){
        binding.rcHours.layoutManager = LinearLayoutManager(activity)
        adapter = HourAdapter()
        rcHours.adapter = adapter
        viewModel.listOfHours.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}