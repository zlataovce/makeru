plugins {
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("makeru")
                description.set("A Java ORM thing, with support for Reactive Streams.")
                url.set("https://github.com/cephxdev/makeru")
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://github.com/cephxdev/makeru/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("zlataovce")
                        name.set("Matouš Kučera")
                        email.set("mk@kcra.me")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/cephxdev/makeru.git")
                    developerConnection.set("scm:git:ssh://github.com/cephxdev/makeru.git")
                    url.set("https://github.com/cephxdev/makeru/tree/master")
                }
            }
        }
    }
}
