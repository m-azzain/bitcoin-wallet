plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // This one has a conflicting version, its version is set in gradle/libs.versions.toml
    // alias(libs.plugins.kapt)
    id("org.jetbrains.kotlin.kapt")
}

android {
    compileSdk = 35
    defaultConfig {
        minSdk = 26
        targetSdk = 34
        versionCode = 102600
        versionName = "10.26"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        // Configurations of Bitcoin Wallet
        // Its role may only be when publishing to playstore
        applicationId = "de.schildbach"
//        applicationId = "com.example.playwithbitcoinwallet"
        // One of its purposes for the BuidConfig and R to be available in this package
        // In the older version of android it will be set as a package property in the manifest
        namespace = "de.schildbach.wallet"
    }

    // Configurations of Bitcoin Wallet
    flavorDimensions += listOf("flavor")
    productFlavors {
        create("dev") {
            dimension = "flavor"
            applicationIdSuffix = ".wallet_test"
        }
        create("prod") {
            dimension = "flavor"
            applicationIdSuffix = ".wallet"
        }
        create("regtest") {
            dimension = "flavor"
            applicationIdSuffix = ".wallet_regtest"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            // They go together
//            isMinifyEnabled = true
//            isShrinkResources = true
            isMinifyEnabled = false
            isShrinkResources = false
            isCrunchPngs = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDirs("res")
            assets.srcDirs("assets")
        }
        getByName("prod") {
            res.srcDirs("res-prod")
            assets.srcDirs("assets-prod")
        }
        getByName("test") {
            java.srcDirs("test")
            resources.srcDirs("test")
        }
    }

    lint {
        checkReleaseBuilds = true
        abortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true

        buildConfig = true
    }
}

// Configurations of Bitcoin Wallet
configurations.all {
    exclude(group = "androidx.viewpager", module = "viewpager")
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Dependencies of Bitcoin Wallet
    //--------------------------------------------
    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.core:core:1.13.1")
    implementation("androidx.activity:activity:1.9.3")
    implementation("androidx.fragment:fragment:1.7.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // The need started from exchangerate
//    implementation("androidx.room:room-runtime:2.6.1")
//    annotationProcessor("androidx.room:room-compiler:2.6.1")
    // This is to use room annotation with kotlin
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation("com.google.guava:guava:33.4.0-android")
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.squareup.okhttp3:okhttp:3.14.9")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.9")
    implementation("com.squareup.moshi:moshi:1.11.0")

    implementation("org.bitcoinj:bitcoinj-core:0.16.5")
    implementation("org.slf4j:slf4j-api:2.0.9")

    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")
    implementation("androidx.lifecycle:lifecycle-service:2.7.0")
    implementation("androidx.sqlite:sqlite:2.4.0")

    implementation("com.github.tony19:logback-android:3.0.0")
}
