package com.sweet.net_monitor.interceptor

import android.net.Uri
import com.sweet.net_monitor.mock.MockHelper
import okhttp3.Interceptor
import okhttp3.Response

class MonitorMockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val path = Uri.parse(url).path
        if (MockHelper.isMockByNetworkResponse(path)) {
            return MockHelper.buildMockServer(chain, request)
        }
        return chain.proceed(request)
    }
}