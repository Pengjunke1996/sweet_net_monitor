package com.sweet.monitor_plugin.asm.transform

import com.didiglobal.booster.transform.Transformer
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.sweet.monitor_plugin.asm.transformer.asm.BaseAsmTransformer
import com.sweet.monitor_plugin.asm.transformer.clazz.net_monitor.NetMonitorTransform
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class CommonTransform(androidProject: Project) : BaseTransform(androidProject) {

    private val classTransformerList = mutableListOf<ClassTransformer>()

    init {
        classTransformerList.add(NetMonitorTransform())
    }

    override val transformers = listOf<Transformer>(
        BaseAsmTransformer(classTransformerList)
    )

    override fun getName(): String {
        return "CommonTransform"
    }

}
