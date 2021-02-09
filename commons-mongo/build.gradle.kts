val kMongoVersion: String by project
val mongoReactionAdapterVersion: String by project

dependencies {
    api(project(":commons-context"))
    api("com.github.jntakpe:tracing-mongo-reactor-adapter:$mongoReactionAdapterVersion")
    api("io.micronaut.mongodb:micronaut-mongo-reactive")
    api("org.litote.kmongo:kmongo-reactor-serialization:$kMongoVersion")
    implementation("io.micronaut:micronaut-inject")
}
