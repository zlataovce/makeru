plugins {
    id("makeru.base-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    api(project(":core"))
    api(libs.reactive.streams)
    compileOnly(libs.jb.annotations)
}
