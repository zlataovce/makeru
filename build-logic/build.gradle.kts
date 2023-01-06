plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    // https://github.com/gradle/gradle/issues/15383
    compileOnly(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}
