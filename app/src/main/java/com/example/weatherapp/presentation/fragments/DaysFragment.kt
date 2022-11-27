package com.example.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentDaysBinding
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.adapters.DaysAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysFragment : Fragment() {
    private var _binding: FragmentDaysBinding?=null
    private val binding get()=_binding!!

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentDaysBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding){
        val adapter = DaysAdapter()
        rcDays.layoutManager= LinearLayoutManager(activity)
        rcDays.adapter=adapter

        viewModel.listOfDays.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()

    }
}