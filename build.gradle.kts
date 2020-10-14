val kotlinVersion: String by project
val micronautVersion: String by project
val reactorVersion: String by project

plugins {
    idea
    `java-library`
    `maven-publish`
    val kotlinVersion = "1.4.10"
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
    version = "0.1.1"

    dependencies {
        kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
        kapt("io.micronaut:micronaut-inject-java")
        implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
        implementation(platform("io.projectreactor:reactor-bom:$reactorVersion"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        withJavadocJar()
        withSourcesJar()
    }

    allOpen {
        annotation("io.micronaut.aop.Around")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("java"))
            }
        }
    }
}
