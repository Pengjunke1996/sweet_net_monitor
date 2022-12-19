package com.sweet.auto_language

import com.sweet.auto_language.xml.ReadXlsToXml
import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoLanguagePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("AutoLanguagePlugin start")
        project.extensions.create("languageConfig", SweetLanguageConfig::class.java)
        project.tasks.register("auto_language") {
            it.doLast {
                doTask(project)
            }
        }
    }

    fun doTask(project: Project) {
        println("AutoLanguagePlugin doTask")
        initConfig(project)
        println("AutoLanguagePlugin after initConfig ${SweetLanguageConfig.config.toString()}")
        val slConfig = SweetLanguageConfig.config ?: return
        ReadXlsToXml.main()

    }

    fun initConfig(project: Project) {
        val sweetLanguage = project.extensions.getByName("languageConfig") as? SweetLanguageConfig
        SweetLanguageConfig.config = sweetLanguage
    }
}