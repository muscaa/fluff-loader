plugins {
    id("java-library")
    id("com.vanniktech.maven.publish") version "0.36.0"
}

group = "dev.musca"
version = System.getenv("GITHUB_REF_NAME")?.removePrefix("v") ?: "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
	// api "com.github.muscaa:fluff-core:1.0.+"
}

tasks.withType<Jar> {
    from(layout.projectDirectory) {
        include("LICENSE", "NOTICE")
        into("META-INF")
    }
}

mavenPublishing {
    publishToMavenCentral(/*automaticRelease = true*/)
    signAllPublications()
    coordinates(project.group.toString(), project.name, project.version.toString())

    pom {
        name.set("Fluff Loader")
        description.set("A library that provides a Runtime Class Loader, allowing you to dynamically load classes, jars, files, and folders at runtime")
        inceptionYear.set("2024")
        url.set("https://github.com/muscaa/fluff-loader/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("muscaa")
                name.set("musca")
                url.set("https://github.com/muscaa/")
            }
        }
        scm {
            url.set("https://github.com/muscaa/fluff-loader/")
            connection.set("scm:git:git://github.com/muscaa/fluff-loader.git")
            developerConnection.set("scm:git:ssh://git@github.com/muscaa/fluff-loader.git")
        }
    }
}
