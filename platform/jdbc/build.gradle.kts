plugins {
    id("makeru.base-conventions")
}

dependencies {
    api(project(":core"))
    compileOnly(libs.jb.annotations)
    testImplementation(libs.h2)
}
