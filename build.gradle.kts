val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
}

group = "pl.edu.amu.wmi"
version = "0.0.1"

application {
    mainClass.set("pl.edu.amu.wmi.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    api("io.insert-koin:koin-ktor:3.4.1")
    api("org.postgresql:postgresql:42.7.3")
    api("com.zaxxer:HikariCP:5.1.0")
    api("org.jetbrains.exposed:exposed-core:0.48.0")
    api("org.jetbrains.exposed:exposed-jdbc:0.48.0")
    api("org.flywaydb:flyway-core:9.0.4")
    api("io.ktor:ktor-server-content-negotiation:$ktor_version")
    api("io.ktor:ktor-serialization-jackson:$ktor_version")
    api("io.ktor:ktor-server-core-jvm")
    api("io.ktor:ktor-server-netty-jvm")
    api("ch.qos.logback:logback-classic:$logback_version")
    runtimeOnly("io.insert-koin:koin-core:3.4.1")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
