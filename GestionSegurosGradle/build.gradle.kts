plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("at.favre.lib:bcrypt:0.9.0")
    implementation ("org.jline:jline:3.21.0")
}

tasks.test {
    useJUnitPlatform()
}