import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "gg.dani.chess"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.3")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    testImplementation(kotlin("test"))
}

javafx {
    version = "11.0.2"
    modules("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("gg.dani.chess.ChessApp")
}