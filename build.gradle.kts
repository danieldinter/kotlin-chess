plugins {
    kotlin("jvm") version "2.2.21"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("maven-publish")
    id("io.kotest") version "6.0.4"
}

kotlin {
    jvmToolchain(24)
}

group = "gg.dani.chess"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.5.20")
    testImplementation("io.kotest:kotest-framework-engine:6.0.4")
    testImplementation("io.kotest:kotest-assertions-table:6.0.4")
    testImplementation("io.kotest:kotest-runner-junit5:6.0.4")
}

javafx {
    version = "24"
    modules("javafx.controls", "javafx.fxml")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

application {
    mainClass.set("gg.dani.chess.ChessApp")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/danieldinter/kotlin-chess")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks.withType<Jar> {
    manifest {
        manifest {
            attributes["Main-Class"] = application.mainClass
        }
    }
}