plugins {
    id("makeru.base-conventions")
    id("makeru.publish-conventions")
}

dependencies {
    api(project(":core"))
    api(libs.reactive.streams)
    compileOnly(libs.jb.annotations)
}
