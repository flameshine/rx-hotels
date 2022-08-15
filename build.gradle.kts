import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

group = "com.flameshine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val slf4jVersion = "1.8.0-beta4"

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}