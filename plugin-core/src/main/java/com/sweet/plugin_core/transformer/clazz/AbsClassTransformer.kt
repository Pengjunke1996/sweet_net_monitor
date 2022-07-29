package com.sweet.plugin_core.transformer.clazz

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.className
import com.sweet.plugin_core.util.isRelease
import org.gradle.api.Project
import org.objectweb.asm.tree.ClassNode

/**
 * 提供拦截方法，可以统一过滤不需要hook的类
 */
open class AbsClassTransformer : ClassTransformer {

    lateinit var project : Project

    fun onCommInterceptor(context: TransformContext, klass: ClassNode): Boolean {
        if (context.isRelease()) {
            return true
        }

        if (klass.className == "module-info") {
            return true
        }

        return false
    }
}