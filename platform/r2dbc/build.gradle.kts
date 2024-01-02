plugins {
    id("makeru.base-conventions")
    id("makeru.publish-conventions")
}

dependencies {
    api(project(":reactor"))
    api(libs.r2dbc.spi)
    implementation(libs.reactor.core)
    compileOnly(libs.jb.annotations)
}
