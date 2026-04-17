package com.example.weatherdemo.presentation.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherdemo.data.ForecastEntity
import com.example.weatherdemo.databinding.ItemForecastBinding

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val items = mutableListOf<ForecastEntity>()

    fun submitList(data: List<ForecastEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastEntity) {
            binding.txtDate.text = item.dateText
            binding.txtForecastTemp.text = "${item.temperature}°C"
            binding.txtForecastDesc.text =
                item.description.ifBlank { "Unknown" }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}