plugins {
    id("makeru.base-conventions")
}

dependencies {
    api(project(":core"))
    api(libs.reactive.streams)
    compileOnly(libs.jb.annotations)
}
