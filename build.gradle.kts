val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.8"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.jetbrains.exposed:exposed-core:0.46.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.48.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.46.0")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("io.ktor:ktor-server-netty:2.3.1")
    implementation("io.ktor:ktor-server-core:2.3.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.43.0")



    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}