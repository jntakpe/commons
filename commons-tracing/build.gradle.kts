dependencies {
    api(project(":commons-context"))
    api(project(":commons-micronaut"))
    api("io.zipkin.brave:brave-context-slf4j")
    api("io.zipkin.brave:brave-instrumentation-grpc")
}
