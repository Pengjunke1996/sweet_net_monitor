package com.sweet.plugin_core.transformer

import org.gradle.api.Project

interface ProjectApplyListener {
    fun onApply(project: Project)
}