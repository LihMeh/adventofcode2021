import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")

    testImplementation(kotlin("test"))

    testImplementation("org.assertj:assertj-core:3.11.1")

    // junit 5
    //testImplementation(platform("org.junit:junit-bom:5.7.0"))
    //testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}