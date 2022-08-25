package com.sweet.net_monitor.enum

import java.io.Serializable

enum class SPValueType(val value: kotlin.String) : Serializable {
    Int("Int"),
    Double("Double"),
    Long("Long"),
    Float("Float"),
    String("String"),
    Boolean("Boolean");
}