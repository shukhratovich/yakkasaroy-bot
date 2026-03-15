import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.3.0"
    id("application")
    id("com.gradleup.shadow") version "9.3.1"
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.telegram:telegrambots:6.9.7.1")
}

kotlin {
    jvmToolchain(17)
}

application {
    // main() Main.kt faylida bo‘lsa odatda MainKt
    mainClass.set("MainKt")
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}

// Fat jar nomini chiroyli qilish (ixtiyoriy)
tasks.withType<ShadowJar> {
    archiveBaseName.set("yakkasaroy-bot")
    archiveVersion.set("1.0")
    archiveClassifier.set("all")
}
