val micronautVersion: String by project
val kMongoVersion: String by project

dependencies {
    api(project(":commons-context"))
    api("io.micronaut.mongodb:micronaut-mongo-reactive")
    api("org.litote.kmongo:kmongo-reactor-serialization:$kMongoVersion")
    implementation("io.micronaut:micronaut-inject")
}
