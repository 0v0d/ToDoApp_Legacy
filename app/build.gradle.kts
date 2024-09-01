plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.example.todoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    // NavHostFragmentのために
    implementation(libs.androidx.navigation.fragment.ktx)
    // Toolbarのために
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(platform(libs.firebase.bom))
    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    kapt(libs.dagger.hilt.android.compiler)

    // メモリリーク検出ライブラリ
    debugImplementation(libs.leakcanary)

    // detektフォーマット
    detektPlugins(libs.detekt.formatting)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.firebase.firestore.ktx)
    // MockitoJUnitRunner
    testImplementation(libs.mockito.core)
    testImplementation(libs.junit)
    testImplementation(libs.dagger.hilt.android.testing)
    kaptTest(libs.dagger.hilt.android.compiler)
    testImplementation(libs.androidx.runner)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)

    // Espresso
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.uiautomator.v18)
}
kapt {
    // エラータイプの修正を有効化
    correctErrorTypes = true
}
detekt {
    parallel = true
    config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    autoCorrect = true // 自動でフォーマット
    basePath = rootDir.absolutePath
}