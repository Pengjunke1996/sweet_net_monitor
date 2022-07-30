package com.sweet.plugin.cost

interface OnMethodCostListener {
    fun onMethodCost(clazzName: String, methodName: String, cost: Long)
}