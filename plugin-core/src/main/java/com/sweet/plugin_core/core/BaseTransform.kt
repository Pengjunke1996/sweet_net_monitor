package com.sweet.plugin_core.core

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.didiglobal.booster.gradle.SCOPE_FULL_WITH_FEATURES
import com.didiglobal.booster.gradle.SCOPE_PROJECT
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.transform.AbstractKlassPool
import com.didiglobal.booster.transform.Transformer
import com.sweet.plugin_core.util.lookupTransformers
import org.gradle.api.Project

/**
 * Represents the transform base
 * DoKitCommTransform 作用于 CommTransformer、BigImgTransformer、UrlConnectionTransformer、GlobalSlowMethodTransformer
 * @author johnsonlee
 */
open class BaseTransform protected constructor(val project: Project) : Transform() {

    /*transformers
     * Preload transformers as List to fix NoSuchElementException caused by ServiceLoader in parallel mode
     * booster 的默认出炉逻辑 DoKit已重写自处理
     */
    internal open val transformers = listOf<Transformer>()

    internal val verifyEnabled = false

    private val android: BaseExtension = project.getAndroid()

    private lateinit var androidKlassPool: AbstractKlassPool

    init {
        project.afterEvaluate {
            androidKlassPool = object : AbstractKlassPool(android.bootClasspath) {}
        }
    }

    val bootKlassPool: AbstractKlassPool
        get() = androidKlassPool

    override fun getName(): String = this.javaClass.simpleName

    override fun isIncremental() = !verifyEnabled

    override fun isCacheable() = !verifyEnabled

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
        when {
            gTransforms.isEmpty() -> mutableSetOf()
            project.plugins.hasPlugin("com.android.library") -> SCOPE_PROJECT
            project.plugins.hasPlugin("com.android.application") -> TransformManager.SCOPE_FULL_PROJECT
            project.plugins.hasPlugin("com.android.dynamic-feature") -> SCOPE_FULL_WITH_FEATURES
            else -> TODO("Not an Android project")
        }

    override fun getReferencedScopes(): MutableSet<in QualifiedContent.Scope> =
        if (gTransforms.isEmpty()) {
            when {
                gTransforms.isEmpty() -> mutableSetOf()
                project.plugins.hasPlugin("com.android.library") -> SCOPE_PROJECT
                project.plugins.hasPlugin("com.android.application") -> TransformManager.SCOPE_FULL_PROJECT
                project.plugins.hasPlugin("com.android.dynamic-feature") -> SCOPE_FULL_WITH_FEATURES
                else -> TODO("Not an Android project")
            }
        }else{
            super.getReferencedScopes()
        }

    final override fun transform(invocation: TransformInvocation) {
        println("transform SweetPlugin" + project.name)
        DelegateTransformInvocation(invocation, this).apply {
            if (isIncremental) {
                doIncrementalTransform()
            } else {
                outputProvider?.deleteAll()
                doFullTransform()
            }
        }
    }

    val gTransforms = run {
        lookupTransformers(project.buildscript.classLoader)
    }


}