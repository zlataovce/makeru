plugins {
    id("makeru.base-conventions")
}

allprojects {
    group = "dev.cephx.makeru"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    apply(plugin = "makeru.base-conventions")
}
