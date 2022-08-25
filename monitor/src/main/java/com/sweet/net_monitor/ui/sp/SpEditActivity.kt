package com.sweet.net_monitor.ui.sp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweet.net_monitor.utils.flowbus.core.observeEvent
import com.sweet.net_monitor.MonitorHelper
import com.sweet.net_monitor.adapter.MonitorSpAdapter
import com.sweet.net_monitor.databinding.ActivitySpEditBinding
import com.sweet.net_monitor.event.RefreshSpEvent
import kotlin.random.Random

class SpEditActivity : AppCompatActivity() {
    private var textSearchKey: String = ""
    val binding by lazy {
        ActivitySpEditBinding.inflate(layoutInflater)
    }
    val adapter by lazy {
        MonitorSpAdapter()
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
        binding.etSearch.doOnTextChanged { text, start, before, count ->
            textSearchKey = text.toString()
            refresh()
        }
        refresh()

    }

    fun refresh() {
        adapter.submitList(MonitorHelper.getSharedPrefsFilesData().toList().filter {
            if (textSearchKey.isBlank()) {
                true
            } else {
                it.first.contains(textSearchKey) || run {
                    var res = false
                    it.second.keys.forEach {
                        if (it.contains(textSearchKey,true)) {
                            res = true
                        }
                    }
                    res
                }
            }
        })
        binding.swipeRefresh.isRefreshing = false
    }
}