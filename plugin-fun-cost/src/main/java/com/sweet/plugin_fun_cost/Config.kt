package com.sweet.plugin_fun_cost

import java.io.File
import java.io.FileNotFoundException

object Config {

    var open: Boolean = false
    var logTraceInfo: Boolean = false

    //一些默认无需插桩的类
    val UNNEED_TRACE_CLASS = arrayOf("Manifest", "binding")

    //插桩配置文件
    var mTraceConfigFile: String? = null

    //需插桩的包
    private val mNeedTracePackageMap: HashSet<String> by lazy {
        HashSet<String>()
    }

    //在需插桩的包范围内的 无需插桩的白名单
    private val mWhiteClassMap: HashSet<String> by lazy {
        HashSet<String>()
    }

    //在需插桩的包范围内的 无需插桩的包名
    private val mWhitePackageMap: HashSet<String> by lazy {
        HashSet<String>()
    }


    fun isNeedTraceClass(fileName: String, simpleName: String): Boolean {
        var isNeed = true
        for (unTraceCls in UNNEED_TRACE_CLASS) {
            if (fileName.contains(unTraceCls)) {
                isNeed = false
                break
            }
        }
        if (simpleName.startsWith("R$")
            || simpleName == "R"
            || simpleName == "BuildConfig"
        )
            isNeed = false
        return isNeed
    }

    //判断是否是traceConfig.txt中配置范围的类
    fun isConfigTraceClass(className: String): Boolean {

        fun isInNeedTracePackage(): Boolean {
            var isIn = false
            mNeedTracePackageMap.forEach {
                if (className.contains(it)) {
                    isIn = true
                    return@forEach
                }

            }
            return isIn
        }

        fun isInWhitePackage(): Boolean {
            var isIn = false
            mWhitePackageMap.forEach {
                if (className.contains(it)) {
                    isIn = true
                    return@forEach
                }

            }
            return isIn
        }

        fun isInWhiteClass(): Boolean {
            var isIn = false
            mWhiteClassMap.forEach {
                if (className == it) {
                    isIn = true
                    return@forEach
                }

            }
            return isIn
        }

        return if (mNeedTracePackageMap.isEmpty()) {
            !(isInWhitePackage() || isInWhiteClass())
        } else {
            if (isInNeedTracePackage()) {
                !(isInWhitePackage() || isInWhiteClass())
            } else {
                false
            }
        }

    }


    /**
     * 解析插桩配置文件
     */
    fun parseTraceConfigFile() {

        println("MethodCost parseTraceConfigFile")
        val traceConfigFile = File(mTraceConfigFile)
        if (!traceConfigFile.exists()) {
            throw FileNotFoundException(
                """
                    Trace config file not exist,traceConfig.txt
                """.trimIndent()
            )
        }

        val configStr = Utils.readFileAsString(traceConfigFile.absolutePath)

        val configArray =
            configStr.split(System.lineSeparator().toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

        if (configArray != null) {
            for (element in configArray) {
                var config = element
                if (config.isNullOrBlank()) {
                    continue
                }
                if (config.startsWith("#")) {
                    continue
                }
                if (config.startsWith("[")) {
                    continue
                }

                when {
                    config.startsWith("-tracepackage ") -> {
                        config = config.replace("-tracepackage ", "")
                        mNeedTracePackageMap.add(config)
                        System.out.println("tracepackage:$config")
                    }
                    config.startsWith("-keepclass ") -> {
                        config = config.replace("-keepclass ", "")
                        mWhiteClassMap.add(config)
                        System.out.println("keepclass:$config")
                    }
                    config.startsWith("-keeppackage ") -> {
                        config = config.replace("-keeppackage ", "")
                        mWhitePackageMap.add(config)
                        System.out.println("keeppackage:$config")
                    }
                    else -> {
                    }
                }
            }

        }


    }

}

