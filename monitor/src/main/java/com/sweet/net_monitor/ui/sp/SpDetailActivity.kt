package com.sweet.net_monitor.ui.sp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweet.net_monitor.utils.flowbus.core.observeEvent
import com.sweet.net_monitor.MonitorHelper
import com.sweet.net_monitor.adapter.MonitorSpDetailAdapter
import com.sweet.net_monitor.databinding.ActivitySpDetailBinding
import com.sweet.net_monitor.event.RefreshSpEvent

class SpDetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_FILE = "KEY_FILE"
        fun startActivity(context: Context, key: String?) {
            context.startActivity(Intent(context, SpDetailActivity::class.java).also {
                it.putExtra(KEY_FILE, key)
            })
        }
    }

    val binding by lazy {
        ActivitySpDetailBinding.inflate(layoutInflater)
    }
    val adapter by lazy {
        MonitorSpDetailAdapter(fileKey) { data, holder ->
            holder.binding.tvHost.text = data.first
            holder.binding.tvPath.text = "${data.second?.type?.value}:${data.second?.value}"
            holder.binding.root.setOnLongClickListener {
                SpEditDialogFragment.showSafe(
                    fileKey = fileKey,
                    data.first,
                    data.second?.type?.value,
                    this.supportFragmentManager
                )
                return@setOnLongClickListener true
            }
        }
    }

    val fileKey by lazy {
        intent?.getStringExtra(KEY_FILE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
        binding.rvMonitor.layoutManager = LinearLayoutManager(this)
        binding.rvMonitor.adapter = adapter

        observeEvent<RefreshSpEvent> {
            refresh()
        }
        binding.tvTitle.text = fileKey
        refresh()

    }

    fun refresh() {
        adapter.submitList(MonitorHelper.getSharedPrefsFilesData()[fileKey]?.toList())
        binding.swipeRefresh.isRefreshing = false
    }
}