plugins {
    id("makeru.base-conventions")
}

allprojects {
    group = "dev.cephx.makeru"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    apply(plugin = "makeru.base-conventions")
}
