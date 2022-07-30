package com.sweet.plugin_fun_cost

import com.google.auto.service.AutoService
import com.sweet.plugin_core.transformer.ProjectApplyListener
import org.gradle.api.Project

@AutoService(ProjectApplyListener::class)
class FunCostProjectApplyImpl : ProjectApplyListener {
    override fun onApply(project: Project) {
        project.extensions.create("sweetCost", SweetCostConfig::class.java)
    }
}