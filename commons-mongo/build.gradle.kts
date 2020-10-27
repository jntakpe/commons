val kMongoVersion: String by project

dependencies {
    api(project(":commons-context"))
    api("com.github.jntakpe:mongo-reactor-adapter:0.1.2")
    api("io.micronaut.mongodb:micronaut-mongo-reactive")
    api("org.litote.kmongo:kmongo-reactor-serialization:$kMongoVersion")
    implementation("io.micronaut:micronaut-inject")
}
