package com.sweet.auto_language

open class SweetLanguageConfig(
    var originUrl: String = "",
    var filePath: String = "",
    var xmlFilePath: String = "",
) {
    companion object{
        var config : SweetLanguageConfig? = null
    }
    override fun toString(): String {
        return "SweetLanguageConfig:${originUrl} $filePath $xmlFilePath"
    }
}

