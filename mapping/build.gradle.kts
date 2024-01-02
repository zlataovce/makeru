plugins {
    id("makeru.base-conventions")
    id("makeru.publish-conventions")
}

dependencies {
    api(project(":core"))
    compileOnly(libs.jb.annotations)
}
