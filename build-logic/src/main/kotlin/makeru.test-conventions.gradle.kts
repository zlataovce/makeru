import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

plugins {
    id("makeru.base-conventions")
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
