pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "io.realm") {
                useModule("io.realm.kotlin:gradle-plugin:1.11.0")
            }
        }
    }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}


rootProject.name = "Therapeutic"
include(":androidApp")
include(":shared")
include(":buildSources")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
