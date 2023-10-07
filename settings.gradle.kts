pluginManagement {
    includeBuild("build-logic")
}

fun includeComposite(name: String, vararg modules: String) {
    modules.forEach { module ->
        include(":$name-$module")
        project(":$name-$module").projectDir = file("$name/$module")
    }
}

rootProject.name = "makeru"

include("core")
includeComposite("platform", "jdbc", "r2dbc")
includeComposite("dialect", "postgresql")
