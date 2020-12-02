val kMongoVersion: String by project

dependencies {
    api(project(":commons-context"))
    api("io.micronaut.redis:micronaut-redis-lettuce")
    implementation("io.micronaut:micronaut-inject")
    compileOnly("io.zipkin.brave:brave")
}
