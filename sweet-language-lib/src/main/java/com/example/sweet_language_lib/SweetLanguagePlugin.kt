package com.example.sweet_language_lib

import com.google.auto.service.AutoService
import com.sweet.plugin_core.transformer.ProjectApplyListener
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

@AutoService
class SweetLanguagePlugin : ProjectApplyListener{
    override fun onApply(project: Project) {
        project.extensions.create("sweetLanguage", LanguageTask::class.java)
    }
}

class LanguageTask : DefaultTask() {

    @TaskAction
    fun run(){
        Excel2Xml().simpleRead()
    }
}

