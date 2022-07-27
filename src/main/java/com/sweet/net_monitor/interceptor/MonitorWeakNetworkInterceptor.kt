package com.sweet.net_monitor.interceptor

import com.sweet.net_monitor.enum.WeakNetworkType
import com.sweet.net_monitor.weaknetwork.WeakNetworkHelper
import okhttp3.Interceptor
import okhttp3.Response

class MonitorWeakNetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WeakNetworkHelper.isOpen) {
            return chain.proceed(chain.request())
        }
        return when (WeakNetworkHelper.weakNetType()) {
            WeakNetworkType.TIME_OUT -> {
                WeakNetworkHelper.mockTimeout(chain)
            }
            WeakNetworkType.SPEED_LIMIT -> {
                WeakNetworkHelper.mockSpeedLimit(chain)
            }
            WeakNetworkType.NO_NETWORK -> {
                WeakNetworkHelper.mockNoNetwork(chain)
            }
        }
    }
}