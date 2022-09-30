package com.sweet.net_monitor.apm.net

import okhttp3.Call

interface NetMonitorCallback {
    fun onSuccess(call: Call, monitorResult: MonitorResult)

    fun onError(call: Call, monitorResult: MonitorResult, ioe: Exception)
}