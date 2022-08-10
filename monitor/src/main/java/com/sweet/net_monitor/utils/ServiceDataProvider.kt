package com.sweet.net_monitor.utils

import com.sweet.net_monitor.MonitorHelper
import com.sweet.net_monitor.data.MonitorData

object ServiceDataProvider {

    fun getMonitorDataList(limit: Int = 50, offset: Int = 0): MutableList<MonitorData> {
        return MonitorHelper.getMonitorDataList(limit, offset)
    }

    fun getMonitorDataByLastId(lastUpdateDataId: Long): MutableList<MonitorData> {
        return MonitorHelper.getMonitorDataByLastId(lastUpdateDataId)
    }
}