plugins {
    alias(libs.plugins.kotlinJvm)//assume server is on jvm
    alias(libs.plugins.ktor)
//    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.kotlin.plugin.serialization)
    application
}

group = "org.elisabethhuhn.networkexample"
version = "1.0.0"
application {
    mainClass.set("org.elisabethhuhn.networkexample.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)

    implementation(libs.bundles.ktor.server)

//    implementation(libs.ktor.server.request.validation)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
}