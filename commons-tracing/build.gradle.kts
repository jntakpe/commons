val braveVersion: String by project

dependencies {
    api(project(":commons-context"))
    api(project(":commons-micronaut"))
    api(platform("io.zipkin.brave:brave-bom:$braveVersion"))
    api("io.zipkin.brave:brave-context-slf4j")
    api("io.zipkin.brave:brave-instrumentation-grpc")
    compileOnly("io.grpc:grpc-core")
}
