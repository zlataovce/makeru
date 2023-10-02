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
