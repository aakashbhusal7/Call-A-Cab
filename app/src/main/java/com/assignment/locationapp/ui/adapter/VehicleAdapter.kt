package com.assignment.locationapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.locationapp.R
import com.assignment.locationapp.data.model.PoiModel
import com.assignment.locationapp.databinding.ItemVehicleBinding
import com.assignment.locationapp.ext.Constants
import com.assignment.locationapp.ext.formatCoordinate
import com.bumptech.glide.Glide

class VehicleAdapter(
    private val poiList: List<PoiModel>
) : RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(val binding: ItemVehicleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleAdapter.ViewHolder {
        val binding = ItemVehicleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            with(holder.binding) {
                checkVehicleType(holder, position)
                tvVehicleId.text = poiList[position].vehicleId.toString()
                tvVehicleType.text = poiList[position].vehicleType
                tvLatitude.text =
                    " ${formatCoordinate(poiList[position].coordinateData?.latitude.toString())}"
                tvLongitude.text =
                    " ${formatCoordinate(poiList[position].coordinateData?.longitude.toString())}"
            }
        }
    }

    //show different icon according to the fleet type
    private fun checkVehicleType(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            when (poiList[position].vehicleType) {
                Constants.POOLING -> {
                    Glide.with(context)
                        .load(R.drawable.ic_pooling)
                        .into(ivVehicleType)
                }
                Constants.TAXI -> {
                    Glide.with(context)
                        .load(R.drawable.ic_taxi)
                        .into(ivVehicleType)
                }
                else -> {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return poiList.size
    }

}