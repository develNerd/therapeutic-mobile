plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

version = "1.0"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvmToolchain(17)

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    val coroutinesVersion = "1.5.2"
    val serializationVersion = "1.3.1"
    val ktorVersion = "2.3.7"


    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                with(Deps.Koin) {
                    api(core)
                    api(test)
                }
                with(Deps.Firebase) {
                    api(auth)
                    api(fireStore)
                }
                with(Deps.Kermit){
                    implementation(kermitMain)
                }
                with(Deps.RealDatabase){
                    implementation(this.coroutines)
                    implementation("io.realm.kotlin:library-base:1.11.0")
                    implementation("io.realm.kotlin:library-sync:1.11.0")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
                    //implementation(this.realBase)
                }
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

    }
}

android {
    compileSdk = 34
    namespace = "com.flepper.therapeutic"
    defaultConfig {
        minSdk = 23
    }
}


