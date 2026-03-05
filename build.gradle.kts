plugins {
    // id "com.github.muscaa.fluff-gradle" version "1.0.12"
    `java-library`
    `maven-publish`
    signing
}

group = "io.github.muscaa"
version = System.getenv("GITHUB_REF_NAME")?.removePrefix("v")

val isCI = System.getenv("GITHUB_ACTIONS") == "true"

// fluff {
// 	include = [
// 		"LICENSE": "META-INF/LICENSE",
// 		"NOTICE": "META-INF/NOTICE"
// 	]
// }

java {
    withSourcesJar()
    withJavadocJar()
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
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = project.group.toString()
            // artifactId = "your-artifact-id"
            version = project.version.toString()
            
            pom {
                name.set("Fluff Loader")
                description.set("A brief description of your library")
                url.set("https://github.com/muscaa/fluff-loader")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("muscaa")
                        name.set("Musca")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/muscaa/fluff-loader.git")
                    url.set("https://github.com/muscaa/fluff-loader")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("CENTRAL_TOKEN_USERNAME")
                password = System.getenv("CENTRAL_TOKEN_PASSWORD")
            }
        }
    }
}

signing {
    val signingKey = System.getenv("GPG_SIGNING_KEY")
    val signingPassword = System.getenv("GPG_SIGNING_KEY_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["maven"])
}
