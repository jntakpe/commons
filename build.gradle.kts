import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val micronautVersion: String by project
val reactorVersion: String by project
val braveVersion: String by project

plugins {
    idea
    `java-library`
    `maven-publish`
    val kotlinVersion = "1.4.20"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
}

allprojects {
    apply(plugin = "idea")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-allopen")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    group = "com.github.jntakpe"
    version = "0.1.14-RC2"

    dependencies {
        kapt(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
        kapt("io.micronaut:micronaut-inject-java")
        api(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
        api(platform("io.projectreactor:reactor-bom:$reactorVersion"))
        api(platform("io.zipkin.brave:brave-bom:$braveVersion"))
        api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        api("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        withJavadocJar()
        withSourcesJar()
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11"
                javaParameters = true
            }
        }
    }

    allOpen {
        annotation("io.micronaut.aop.Around")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components.findByName("java"))
                pom {
                    name.set(project.name)
                    description.set("Common's library")
                    url.set("https://github.com/equidis/commons")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("jntakpe")
                            name.set("Jocelyn NTAKPE")
                        }
                    }
                    issueManagement {
                        system.set("Github issues")
                        url.set("https://github.com/equidis/commons/issues")
                    }
                    ciManagement {
                        system.set("Github actions")
                        url.set("https://github.com/equidis/commons/actions")
                    }
                    scm {
                        connection.set("scm:git:git@github.com:equidis/commons.git")
                        developerConnection.set("scm:git:git@github.com:equidis/commons.git")
                        url.set("https://github.com/equidis/commons/")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "Github_packages"
                setUrl("https://maven.pkg.github.com/equidis/commons")
                credentials {
                    val githubActor: String? by project
                    val githubToken: String? by project
                    username = githubActor
                    password = githubToken
                }
            }
        }
    }
}
