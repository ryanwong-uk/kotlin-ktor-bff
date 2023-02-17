@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.kover)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.kotest)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
}

group = "com.rwmobi"
version = libs.versions.application.version.get()

application {
    mainClass.set("io.ktor.server.cio.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ch.qos.logback)
    implementation(libs.io.dropwizard.metrics)
    testImplementation(libs.ktor.server.tests.jvm)
    testImplementation(libs.kotlin.test.junit)
}

apply(plugin = "kover")

extensions.configure<kotlinx.kover.api.KoverMergedConfig> {
    enable()
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    disabledRules.set(
        setOf(
            "max-line-length",
            "trailing-comma-on-declaration-site",
            "trailing-comma-on-call-site"
        )
    )
}

kover {
    isDisabled.set(false) // true to disable instrumentation and all Kover tasks in this project
    engine.set(kotlinx.kover.api.DefaultIntellijEngine)
    filters {
        classes {
            excludes.addAll(
                listOf(
                    "uk.ryanwong.gmap2ics.app.configs.*",
                    "uk.ryanwong.gmap2ics.ui.screens.*",
                    "uk.ryanwong.gmap2ics.ComposableSingletons*",
                    "uk.ryanwong.gmap2ics.ui.theme.*",
                    "uk.ryanwong.gmap2ics.MainKt"
                )
            )
        }
    }

    xmlReport {
        // true to run koverXmlReport task during the execution of the check task (if it exists) of the current project
        onCheck.set(true)
    }

    htmlReport {
        // true to run koverHtmlReport task during the execution of the check task (if it exists) of the current project
        onCheck.set(true)
    }

    verify {
        // true to run koverVerify task during the execution of the check task (if it exists) of the current project
        onCheck.set(true)
    }
}
