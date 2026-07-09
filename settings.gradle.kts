pluginManagement {
    repositories {
        maven("https://redirector.gvt1.com/edgedl/android/maven2/")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://redirector.gvt1.com/edgedl/android/maven2/")
        google()
        mavenCentral()
    }
}

rootProject.name = "Talangor"
include(":app")
