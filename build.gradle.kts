val kotlinVersion: String by project
val micronautVersion: String by project
val reactorVersion: String by project
val kMongoVersion: String by project

plugins {
    idea
    `java-library`
    `maven-publish`
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
}

group = "com.github.jntakpe"
version = "0.1.0"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation(platform("io.projectreactor:reactor-bom:$reactorVersion"))
    implementation("org.litote.kmongo:kmongo-async-serialization:$kMongoVersion")
    implementation("io.grpc:grpc-core")
    implementation("org.slf4j:slf4j-api")
    implementation("io.projectreactor:reactor-core")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenProto") {
            from(components.findByName("java"))
        }
    }
}
