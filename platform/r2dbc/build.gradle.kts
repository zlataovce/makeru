plugins {
    id("makeru.base-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    api(project(":core"))
    api(libs.r2dbc.spi)
    compileOnly(libs.jb.annotations)
}