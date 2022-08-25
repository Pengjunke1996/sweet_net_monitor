package com.sweet.net_monitor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweet.net_monitor.data.SpValueInfo
import com.sweet.net_monitor.databinding.ItemMonitorSpBinding
import com.sweet.net_monitor.ui.sp.SpDetailActivity

class MonitorSpAdapter :
    ListAdapter<Pair<String, HashMap<String, SpValueInfo?>>, MonitorSpAdapter.MonitorSpViewHolder>(
        object : DiffUtil.ItemCallback<Pair<String, HashMap<String, SpValueInfo?>>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, HashMap<String, SpValueInfo?>>,
                newItem: Pair<String, HashMap<String, SpValueInfo?>>
            ): Boolean {
                return oldItem.first == newItem.first
            }

            override fun areContentsTheSame(
                oldItem: Pair<String, HashMap<String, SpValueInfo?>>,
                newItem: Pair<String, HashMap<String, SpValueInfo?>>
            ): Boolean {
                return false
            }

        }
    ) {

    class MonitorSpViewHolder(val binding: ItemMonitorSpBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitorSpViewHolder {
        return MonitorSpViewHolder(
            ItemMonitorSpBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonitorSpViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.tvHost.text = data.first
        holder.binding.tvPath.text = run {
            val buildStr = StringBuilder()
            data.second.forEach {
                buildStr.append(it.key).append("\n")
            }
            buildStr.toString()
        }
        holder.binding.tvCode.text = data.second.size.toString()

        holder.binding.root.setOnClickListener {
            SpDetailActivity.startActivity(holder.binding.root.context,data.first)
        }
    }
}