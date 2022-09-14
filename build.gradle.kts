import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
    idea
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

group = "example.ktlint"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springWebVersion")}")

    testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${property("springWebVersion")}")
    testImplementation("org.testcontainers:junit-jupiter:${property("testcontainersVersion")}")
}

idea {
    module {
        inheritOutputDirs = false
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    withType<Test> {
        useJUnitPlatform { }
    }

    // ktlint {
    //     version.set("0.44.0") // Pinterest version
    //     debug.set(true)
    //     verbose.set(true)
    //     android.set(false)
    //     ignoreFailures.set(true)
    //     disabledRules.set(setOf("indent"))
    //     enableExperimentalRules.set(true)
    //     reporters {
    //         reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
    //         reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    //
    //         customReporters {
    //             register("html") {
    //                 fileExtension = "html"
    //                 dependency = "com.pinterest.ktlint:ktlint-reporter-html:0.38.1"
    //             }
    //         }
    //     }
    //     kotlinScriptAdditionalPaths {
    //         include(fileTree("scripts/"))
    //     }
    //     filter {
    //         exclude("**/*Test*")
    //     }
    // }
}
