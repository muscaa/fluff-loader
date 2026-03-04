plugins {
    // id "com.github.muscaa.fluff-gradle" version "1.0.12"
    `java-library`
    `maven-publish`
    signing
}

val isCI = System.getenv("GITHUB_ACTIONS") == "true"

// fluff {
// 	include = [
// 		"LICENSE": "META-INF/LICENSE",
// 		"NOTICE": "META-INF/NOTICE"
// 	]
// }

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    // maven { url "https://jitpack.io" }
}

dependencies {
	// api "com.github.muscaa:fluff-core:1.0.+"
}

// publishing {
//     repositories {
//         maven {
//             name = "OSSRH"
//             url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
//             credentials {
//                 username = System.getenv("MAVEN_USERNAME")
//                 password = System.getenv("MAVEN_PASSWORD")
//             }
//         }
//     }
// }

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            
            pom {
                name.set("Fluff Loader")
                description.set("A concise description of what it does")
                url.set("https://github.com/muscaa/fluff-loader")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                // developers {
                //     developer {
                //         id.set("yourusername")
                //         name.set("Your Name")
                //     }
                // }
                // scm {
                //     connection.set("scm:git:git://github.com/username/repo.git")
                //     developerConnection.set("scm:git:ssh://github.com/username/repo.git")
                //     url.set("https://github.com/username/repo")
                // }
            }
        }
    }

    repositories {
        maven {
            // "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            val stagingRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotRepoUrl else stagingRepoUrl)
            
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

signing {
    if (isCI) {
        val key = System.getenv("MAVEN_GPG_KEY")
        val passphrase = System.getenv("MAVEN_GPG_PASSPHRASE")
        useInMemoryPgpKeys(key, passphrase)
    } else {
        useGpgCmd()
    }
    sign(publishing.publications["mavenJava"])
}
