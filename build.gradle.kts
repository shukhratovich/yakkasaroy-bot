plugins {
    kotlin("jvm") version "2.3.0"
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.telegram:telegrambots:6.9.7.1")
}

kotlin {
    jvmToolchain(17)
}

application {
    // Agar main() Main.kt faylida bo‘lsa — odatda MainKt bo‘ladi
    mainClass.set("MainKt")
}

// (ixtiyoriy, lekin foydali) jar ichida Main-Class yozib qo‘yadi
tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}
