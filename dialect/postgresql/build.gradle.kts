plugins {
    id("makeru.base-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    api(project(":core"))
    compileOnly(libs.jb.annotations)
}
