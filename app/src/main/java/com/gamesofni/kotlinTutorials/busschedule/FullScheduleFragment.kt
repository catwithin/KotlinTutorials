/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gamesofni.kotlinTutorials.busschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gamesofni.kotlinTutorials.busschedule.viewmodels.BusScheduleViewModel
import com.gamesofni.kotlinTutorials.busschedule.viewmodels.BusScheduleViewModelFactory
import com.gamesofni.kotlinTutorials.databinding.BusscheduleFullScheduleFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FullScheduleFragment: Fragment() {

    private var _binding: BusscheduleFullScheduleFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BusscheduleFullScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // redirecting with stopName parameter
        val busStopAdapter = BusStopAdapter {
            val action = FullScheduleFragmentDirections
                .actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = busStopAdapter

        // using flow returned from fullSchedule() to call submitList when .collect() is called
        // (?? TODO)
        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
