plugins {
    id("makeru.base-conventions")
    id("makeru.publish-conventions")
}

dependencies {
    api(project(":core"))
    api(libs.reactor.core)
    compileOnly(libs.jb.annotations)
}
