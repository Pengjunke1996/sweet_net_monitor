package com.sweet.sweet_net_tool

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.os.Process
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sweet.plugin.cost.CostPlugin
import com.sweet.plugin.cost.OnMethodCostListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        CostPlugin.addListener(object : OnMethodCostListener {
            override fun onMethodCost(clazzName: String, methodName: String, cost: Long) {
                Log.e("cost", "$clazzName  $methodName  $cost")
            }
        })
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("pengjunke", "onCreate: ${ Runtime.getRuntime().maxMemory()* 1.0/ (1024 * 1024)} + ${Runtime.getRuntime().totalMemory()* 1.0/ (1024 * 1024)}  + ${Runtime.getRuntime().freeMemory()* 1.0/ (1024 * 1024)} + ${getMemoryData()}");

    }

    private fun getMemoryData(): Float {
        var mem = 0.0f
        try {
            var memInfo: Debug.MemoryInfo? = null
            //28 为Android P
            if (Build.VERSION.SDK_INT > 28) {
                // 统计进程的内存信息 totalPss
                memInfo = Debug.MemoryInfo()
                Debug.getMemoryInfo(memInfo)
            } else {
                //As of Android Q, for regular apps this method will only return information about the memory info for the processes running as the caller's uid;
                // no other process memory info is available and will be zero. Also of Android Q the sample rate allowed by this API is significantly limited, if called faster the limit you will receive the same data as the previous call.
                val memInfos: Array<Debug.MemoryInfo> = (getSystemService(Context.ACTIVITY_SERVICE)as ActivityManager).getProcessMemoryInfo(
                    intArrayOf(
                        Process.myPid()
                    )
                )
                if (memInfos.isNotEmpty()) {
                    memInfo = memInfos[0]
                }
            }
            var totalPss = 0
            if (memInfo != null) {
                totalPss = memInfo.totalPss
            }
            if (totalPss >= 0) {
                // Mem in MB
                mem = totalPss / 1024.0f
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mem
    }
}