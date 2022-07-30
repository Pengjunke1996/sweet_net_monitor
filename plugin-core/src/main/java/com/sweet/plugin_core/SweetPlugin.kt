package com.sweet.plugin_core

import com.android.build.gradle.AppExtension
import com.didiglobal.booster.gradle.getAndroid
import com.sweet.plugin_core.core.CommonTransform
import com.sweet.plugin_core.transformer.ProjectApplyListener
import com.sweet.plugin_core.util.println
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

/**
 * @author peng tongxue
 * @date 2021/11/13
 */
class SweetPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val list = ServiceLoader.load(ProjectApplyListener::class.java).toList()
        list.forEach {
            it.onApply(project)
        }
        when {
            project.plugins.hasPlugin("com.android.application") ||
                    project.plugins.hasPlugin("com.android.dynamic-feature") -> {

                project.getAndroid<AppExtension>().let { androidExt ->

                    androidExt.registerTransform(
                        CommonTransform(project)
                    )

                    /**
                     * 所有项目的build.gradle执行完毕
                     * wiki:https://juejin.im/post/6844903607679057934
                     *
                     * **/
                    project.gradle.projectsEvaluated {
                        "===projectsEvaluated===".println()
                        androidExt.applicationVariants.forEach { variant ->
//                            PluginConfigProcessor(project).process(variant)
                        }

                    }
                }

            }

            project.plugins.hasPlugin("com.android.library") -> {

            }
        }
    }
}