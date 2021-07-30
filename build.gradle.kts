import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "com.almeydajuan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}