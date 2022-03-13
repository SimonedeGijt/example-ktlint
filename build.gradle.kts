import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    idea
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("com.softeq.gradle.itest") version "1.0.4"
    id("com.github.ben-manes.versions") version "0.42.0"
}

group = "example.ktlint"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations {
    itestImplementation.get().extendsFrom(testImplementation.get())
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

        testSourceDirs.plusAssign(sourceSets["itest"].allSource.srcDirs)
        testResourceDirs.plusAssign(project.sourceSets["itest"].resources.srcDirs)
    }
}

fun setupEnv(task: Test) {
    val env = System.getenv("ENV") ?: "test"
    task.environment("SMOKE_TEST_ENV", env)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    withType<Test> {
        useJUnitPlatform {
            excludeTags("smoke")
        }
    }

    ktlint {
        version.set("0.44.0")
        // debug.set(true)
        // verbose.set(true)
        // android.set(false)
        // ignoreFailures.set(true)
        // enableExperimentalRules.set(true)
        // disabledRules.set(setOf("indent"))
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)

            customReporters {
                register("html") {
                    fileExtension = "html"
                    dependency = "com.pinterest.ktlint:ktlint-reporter-html:0.38.1"
                }
            }
        }
        // kotlinScriptAdditionalPaths {
        //     include(fileTree("scripts/"))
        // }
        // filter {
        //     exclude("**/*Test*")
        // }
    }
}
