plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.dependencyUpdates) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("org.sonarqube") version "7.3.1.8318"
}

sonar {
  properties {
    property("sonar.projectKey", "wenhan-sqc-org_kmp-production-sample")
    property("sonar.organization", "wenhan-sqc-org")
  }
}

allprojects {
    // ./gradlew dependencyUpdates
    // Report: build/dependencyUpdates/report.txt
    apply(plugin = "com.github.ben-manes.versions")
}
