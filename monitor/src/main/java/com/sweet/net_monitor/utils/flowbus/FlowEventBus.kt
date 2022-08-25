package com.sweet.net_monitor.utils.flowbus

import android.app.Application
import com.sweet.net_monitor.utils.flowbus.util.ILogger

object FlowEventBus {

    lateinit var application: Application

    var logger: ILogger? = null

    fun init(application: Application, logger: ILogger? = null) {
        FlowEventBus.application = application
        FlowEventBus.logger = logger
    }

}