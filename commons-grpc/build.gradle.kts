val grpcPgvVersion: String by project
val grpcServicesVersion: String by project
val grpcReactorVersion: String by project

dependencies {
    api(project(":commons-context"))
    api("io.micronaut.grpc:micronaut-grpc-runtime")
    api("io.envoyproxy.protoc-gen-validate:pgv-java-grpc:$grpcPgvVersion")
    api("io.grpc:grpc-services:$grpcServicesVersion")
    api("com.salesforce.servicelibs:reactor-grpc-stub:$grpcReactorVersion")
}
