plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
//    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
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

//    implementation(libs.ktor.client.auth)
//    implementation(libs.ktor.client.apache)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)

//    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}