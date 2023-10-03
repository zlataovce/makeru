import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions")
    `java-library`
}

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

// expose version catalog
val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

dependencies {
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val versionRegex = "^[0-9,.v-]+(-r)?$".toRegex()
val stableKeywords = listOf("RELEASE", "FINAL", "GA")

fun isStable(version: String): Boolean {
    val normalVersion = version.uppercase()

    return stableKeywords.any(normalVersion::contains) || version.matches(versionRegex)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isStable(currentVersion) && !isStable(candidate.version)
    }
}
