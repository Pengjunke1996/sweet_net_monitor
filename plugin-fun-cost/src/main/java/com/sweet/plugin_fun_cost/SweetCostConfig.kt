package com.sweet.plugin_fun_cost;

open class SweetCostConfig(
    var open: Boolean = true,
    var traceConfigFile: String = "",
    var logTraceInfo: Boolean = true
) {
    override fun toString(): String {
        return "SweetCostConfig:${open} $traceConfigFile $logTraceInfo"
    }
}