import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {

    //kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // android
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val preference_ktx = "androidx.preference:preference-ktx:${Versions.preference_ktx}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_livedata_ktx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val material = "com.google.android.material:material:${Versions.material}"

    // dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val dagger_android_processor =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val assisted_inject_annotations =
        "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assisted_inject}"
    const val assisted_inject_processor =
        "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.assisted_inject}"
    const val hilt_android =
        "com.google.dagger:hilt-android:${Versions.hilt_android}"
    const val hilt_android_compiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_android}"
    const val hilt_lifecycle_viewmodel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_androidx}"
    const val hilt_work =
        "androidx.hilt:hilt-work:${Versions.hilt_androidx}"
    const val hilt_androidx_compiler =
        "androidx.hilt:hilt-compiler:${Versions.hilt_androidx}"

    // reactive
    const val rx_preferences =
        "com.f2prateek.rx.preferences2:rx-preferences:${Versions.rx_preferences}"

    //coroutines
    const val coroutine_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val coroutine_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"

    //google
    const val maps = "com.google.maps.android:maps-ktx:${Versions.maps}"
    const val mapUtils = "com.google.maps.android:maps-utils-ktx:${Versions.maps}"
    const val location = "com.google.android.gms:play-services-location:${Versions.location}"

    //sdp
    const val sdp = "com.intuit.sdp:sdp-android:${Versions.sdp}"
    const val ssp = "com.intuit.ssp:ssp-android:${Versions.sdp}"

    //glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    // network
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp_logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val chuckerDebug = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"

    // debug
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // test
    const val junit = "junit:junit:${Versions.junit}"
    const val test_core = "androidx.test:core:${Versions.test_core}"
    const val test_runner = "androidx.test.ext:junit:${Versions.test_runner}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val mockito_android = "io.mockk:mockk:${Versions.mockito}"
    const val okhttp_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    const val arch_core_testing = "androidx.arch.core:core-testing:${Versions.arch_version}"
    const val coroutine_testing =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine_test}"
    const val truth_google =
        "com.google.truth:truth:${Versions.truth}"
    const val hilt_testing = "com.google.dagger:hilt-android-testing:${Versions.hilt_android}"
    const val jraska_test = "com.jraska.livedata:testing-ktx:${Versions.jraska}"

    fun DependencyHandler.base() {
        implementation(kotlin)
        implementation(core_ktx)
        implementation(appcompat)
        implementation(preference_ktx)
        implementation(constraintlayout)
        implementation(material)
    }

    fun DependencyHandler.lifecycle() {
        implementation(lifecycle_viewmodel_ktx)
        implementation(lifecycle_livedata_ktx)
        implementation(lifecycle_common)
        testImplementation(arch_core_testing)
        androidTestImplementation(arch_core_testing)
    }

    fun DependencyHandler.navigation() {
        implementation(navigation_fragment_ktx)
        implementation(navigation_ui_ktx)
    }

    fun DependencyHandler.dagger() {
        implementation(dagger)
        implementation(dagger_android)
        implementation(dagger_android_support)
        kapt(dagger_compiler)
        kapt(dagger_android_processor)
        compileOnly(assisted_inject_annotations)
        kapt(assisted_inject_processor)
        implementation(hilt_android)
        kapt(hilt_android_compiler)
        implementation(hilt_lifecycle_viewmodel)
        implementation(hilt_work)
        kapt(hilt_androidx_compiler)
    }

    fun DependencyHandler.googleLibs() {
        implementation(maps)
        implementation(mapUtils)
        implementation(location)
    }

    fun DependencyHandler.reactive() {
        implementation(rx_preferences)
    }

    fun DependencyHandler.coroutine() {
        implementation(coroutine_android)
        implementation(coroutine_core)
    }

    fun DependencyHandler.layoutSizing() {
        implementation(sdp)
        implementation(ssp)
    }

    fun DependencyHandler.ui() {
        implementation(glide)
    }

    fun DependencyHandler.network() {
        implementation(okhttp)
        implementation(okhttp_logging_interceptor)
        implementation(retrofit)
        implementation(retrofit_converter_gson)
        debugImplementation(chuckerDebug)
        releaseImplementation(chuckerRelease)
        testImplementation(okhttp_mockwebserver)
    }

    fun DependencyHandler.debug() {
        implementation(timber)
    }

    fun DependencyHandler.test() {
        testImplementation(junit)
        testImplementation(test_core)
        testImplementation(robolectric)
        testImplementation(mockito_android)
        testImplementation(coroutine_testing)
        testImplementation(truth_google)
        testImplementation(jraska_test)
    }

    fun DependencyHandler.androidTest() {
        androidTestImplementation(test_runner)
        androidTestImplementation(espresso_core)
        androidTestImplementation(mockito_android)
        androidTestImplementation(coroutine_testing)
        androidTestImplementation(truth_google)
        androidTestImplementation(hilt_testing)
    }

    fun DependencyHandler.kaptAndroidTest() {
        kaptAndroidTest(hilt_android_compiler)
    }

    fun DependencyHandler.kapt() {
        kapt(hilt_android_compiler)
    }

    private fun DependencyHandler.implementation(depName: String) {
        add("implementation", depName)
    }

    private fun DependencyHandler.kapt(depName: String) {
        add("kapt", depName)
    }

    private fun DependencyHandler.compileOnly(depName: String) {
        add("compileOnly", depName)
    }

    private fun DependencyHandler.api(path: String) {
        add("api", project(path))
    }

    private fun DependencyHandler.debugImplementation(depName: String) {
        add("debugImplementation", depName)
    }

    private fun DependencyHandler.releaseImplementation(depName: String) {
        add("releaseImplementation", depName)
    }

    private fun DependencyHandler.testImplementation(depName: String) {
        add("testImplementation", depName)
    }

    private fun DependencyHandler.androidTestImplementation(depName: String) {
        add("androidTestImplementation", depName)
    }

    private fun DependencyHandler.kaptAndroidTest(depName: String) {
        add("kaptAndroidTest", depName)
    }

    private fun DependencyHandler.kaptTest(depName: String) {
        add("kaptTest", depName)

    }
}
