val kMongoVersion: String by project

dependencies {
    api(project(":commons-context"))
    api("com.github.jntakpe:tracing-mongo-reactor-adapter:0.2.0-RC3")
    api("io.micronaut.mongodb:micronaut-mongo-reactive")
    api("org.litote.kmongo:kmongo-reactor-serialization:$kMongoVersion")
    implementation("io.micronaut:micronaut-inject")
    compileOnly("io.zipkin.brave:brave-instrumentation-mongodb")
}
