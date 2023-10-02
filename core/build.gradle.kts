plugins {
    id("makeru.test-conventions")
    alias(libs.plugins.lombok)
}

dependencies {
    compileOnly(libs.jb.annotations)
}
