package com.sweet.sweet_net_tool

import android.os.Bundle
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
    }
}