plugins {
    id("makeru.base-conventions")
    id("makeru.publish-conventions")
}

dependencies {
    api(project(":reactor"))
    api(libs.r2dbc.spi)
    compileOnly(libs.jb.annotations)
    testImplementation(libs.r2dbc.h2) {
        constraints {
            testImplementation(libs.h2) {
                because("we want to keep the h2 version consistent for all tests")
            }
        }
    }
}
