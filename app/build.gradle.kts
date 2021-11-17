import Dependencies.androidTest
import Dependencies.base
import Dependencies.coroutine
import Dependencies.dagger
import Dependencies.debug
import Dependencies.googleLibs
import Dependencies.kapt
import Dependencies.kaptAndroidTest
import Dependencies.layoutSizing
import Dependencies.lifecycle
import Dependencies.navigation
import Dependencies.network
import Dependencies.reactive
import Dependencies.test
import Dependencies.ui
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.31"
}

val versionMajor = Versions.versionMajor
val versionMinor = Versions.versionMinor
val versionPatch = Versions.versionPatch

val apiUrl = "\"https://fake-poi-api.mytaxi.com/\""

android {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
        applicationId =
            "com.assignment.locationapp".also { resValue("string", "application_id", it) }
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = "$versionMajor.$versionMinor.$versionPatch"
        testInstrumentationRunner = "com.assignment.locationapp.AndroidJUnitRunner"
        buildConfigField("String", "FULL_VERSION_NAME", "\"$versionName.$versionCode\"")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.putAll(
                    mapOf(
                        "dagger.hilt.disableModulesHaveInstallInCheck" to "true"
                    )
                )
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", apiUrl)
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", apiUrl)
        }
    }

    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    noArg {
        annotation("com.my.Annotation")
        invokeInitializers = true
    }

    // For assisted-inject library.
    // https://kotlinlang.org/docs/reference/kapt.html#non-existent-type-correction
    kapt {
        correctErrorTypes = true
    }

    configurations.forEach { it.exclude("com.google.guava", "listenablefuture") }

    // Better output apk naming : {projectName}_{flavor(s)}_{buildType}_{versionName}_build_{buildVersion}.apk.
    applicationVariants.all {
        outputs.all {
            val outputImpl = this as BaseVariantOutputImpl
            val sep = "_"
            val version = versionName
            val build = versionCode
            var flavors = ""
            productFlavors.forEach { flavor ->
                flavors += "${flavor.name}${sep}"
            }
            outputImpl.outputFileName = "${rootProject.name}${sep}" +
                    flavors +
                    "${buildType.name}${sep}" +
                    "${version}${sep}" +
                    "build${sep}${build}.apk"
        }
    }
}

dependencies {
    base()
    lifecycle()
    navigation()
    reactive()
    coroutine()
    layoutSizing()
    ui()
    googleLibs()
    network()
    debug()
    test()
    androidTest()
    dagger()
    kapt()
    kaptAndroidTest()
}
