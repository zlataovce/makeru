plugins {
    id("makeru.base-conventions")
}

dependencies {
    api(project(":reactor"))
    api(libs.r2dbc.spi)
    implementation(libs.reactor.core)
    compileOnly(libs.jb.annotations)
}
