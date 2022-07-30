package com.sweet.plugin_core.core

import com.didiglobal.booster.transform.Transformer
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.sweet.plugin_core.transformer.asm.BaseAsmTransformer
import com.sweet.plugin_core.transformer.clazz.AbsClassTransformer
import org.gradle.api.Project
import java.util.*

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class CommonTransform(androidProject: Project) : BaseTransform(androidProject) {

    private val classTransformerList = mutableListOf<ClassTransformer>()

    init {
        val transList =
            ServiceLoader.load(AbsClassTransformer::class.java).toList()
        transList.forEach {
            classTransformerList.add(it)
            it.project = project
        }

    }

    override val transformers = run {


        listOf<Transformer>(
            BaseAsmTransformer(classTransformerList)
        )
    }

    override fun getName(): String {
        return "CommonTransform"
    }

}
