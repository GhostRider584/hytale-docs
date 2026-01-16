plugins {
    id("java")
    id("idea")
    id("fr.smolder.hytale.dev") version "0.0.3"
    id("fr.smolder.javadoc.migration") version "0.0.1"
    `maven-publish`
}

group = "fr.smolder"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.compileJava {
    enabled = false
}

hytale {
    patchLine.set("release")
    gameVersion.set("latest")
    autoUpdateManifest.set(false)

    vineflowerVersion.set("1.11.2")
    decompileFilter.set(listOf("com/hypixel/**"))
    decompilerHeapSize.set("6G")
}

javadocMigration {
    // oldSources.set(file("documented-sources"))
    docsFile.set(file("javadocs-export.json"))
    newJar.set(hytale.serverJar)
    outputDir.set(layout.buildDirectory.dir("migrated-sources"))
    decompileFilter.set(listOf("com/hypixel/**"))
}

publishing {
    repositories {
        maven {
            name = "Smolder"
            url = uri("https://repo.smolder.fr/public/")
            credentials {
                username = project.findProperty("smolderUsername") as String?
                password = project.findProperty("smolderPassword") as String?
            }
        }
    }
}
