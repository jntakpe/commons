val slf4jVersion: String by project

dependencies {
    api(project(":commons-context"))
    api(project(":commons-micronaut"))
    api("io.zipkin.brave:brave-context-slf4j")
    api("io.zipkin.brave:brave-instrumentation-grpc")
    api("org.slf4j:jul-to-slf4j:$slf4jVersion")
}
