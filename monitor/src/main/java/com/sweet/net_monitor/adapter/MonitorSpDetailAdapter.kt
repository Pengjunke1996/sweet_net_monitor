package com.sweet.net_monitor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweet.net_monitor.data.SpValueInfo
import com.sweet.net_monitor.databinding.ItemMonitorSpDetailBinding

class MonitorSpDetailAdapter(val fileKey: String?,val onBindView:(data:Pair<String,SpValueInfo?>, holder:MonitorSpDetailAdapter.MonitorSpDetailViewHolder)->Unit) :
    ListAdapter<Pair<String, SpValueInfo?>, MonitorSpDetailAdapter.MonitorSpDetailViewHolder>(
        object : DiffUtil.ItemCallback<Pair<String, SpValueInfo?>>() {

            override fun areItemsTheSame(
                oldItem: Pair<String, SpValueInfo?>,
                newItem: Pair<String, SpValueInfo?>
            ): Boolean {
                return oldItem.first == newItem.first
            }

            override fun areContentsTheSame(
                oldItem: Pair<String, SpValueInfo?>,
                newItem: Pair<String, SpValueInfo?>
            ): Boolean {
                return oldItem.second === newItem.second
            }

        }
    ) {

    inner class MonitorSpDetailViewHolder(val binding: ItemMonitorSpDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitorSpDetailViewHolder {
        return MonitorSpDetailViewHolder(
            ItemMonitorSpDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonitorSpDetailViewHolder, position: Int) {
        val data = getItem(position)
        onBindView(data,holder)
    }
}