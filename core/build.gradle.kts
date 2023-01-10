plugins {
    id("makeru.test")
    alias(libs.plugins.lombok)
}

dependencies {
    compileOnly(libs.jb.annotations)
}
