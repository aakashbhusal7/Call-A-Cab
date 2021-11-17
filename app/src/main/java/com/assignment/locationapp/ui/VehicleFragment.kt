package com.assignment.locationapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.locationapp.R
import com.assignment.locationapp.data.model.PoiModel
import com.assignment.locationapp.data.model.VehicleResponse
import com.assignment.locationapp.databinding.FragmentVehicleBinding
import com.assignment.locationapp.ext.Resource
import com.assignment.locationapp.ui.adapter.VehicleAdapter
import com.assignment.locationapp.ui.viewmodel.VehicleViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class VehicleFragment : Fragment() {

    private var _binding: FragmentVehicleBinding? = null
    private val vehicleViewModel by viewModels<VehicleViewModel>()
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var action: NavDirections

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVehicleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        loadData()
    }

    private fun setUpClickListeners() {
        binding.btnSeeMap.setOnClickListener {
            action = VehicleFragmentDirections.actionFirstFragmentToSecondFragment()
            if (findNavController().currentDestination?.id == R.id.FirstFragment) {
                findNavController().navigate(action)
            }
        }
    }

    private fun loadData() {
        vehicleViewModel.loadVehicleData().observe(viewLifecycleOwner, {
            it?.let { resource ->
                handleVehicleData(resource)
            }
        })
    }

    private fun handleVehicleData(resource: Resource<VehicleResponse>) {
        hideProgressBar()
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.poiList?.let { vehicleList ->
                    displayData(vehicleList)
                }
            }
            Resource.Status.ERROR, Resource.Status.NO_INTERNET -> {
                resource.message?.let {
                    Toast.makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Resource.Status.LOADING -> showProgressBar()
        }
    }

    private fun displayData(vehicleData: List<PoiModel>) {
        showRecyclerView()
        if (!vehicleData.isNullOrEmpty()) setUpRecyclerView(vehicleData)
        else hideRecyclerView()
    }

    private fun setUpRecyclerView(data: List<PoiModel>) {
        with(binding) {
            rvVehicles.layoutManager = LinearLayoutManager(requireContext())
            vehicleAdapter = VehicleAdapter(data)
            rvVehicles.adapter = vehicleAdapter
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showRecyclerView() {
        with(binding) {
            rvVehicles.visibility = View.VISIBLE
            tvNotFound.visibility = View.GONE
        }
    }

    private fun hideRecyclerView() {
        with(binding) {
            rvVehicles.visibility = View.GONE
            tvNotFound.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}