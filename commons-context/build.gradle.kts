val slf4jVersion: String by project

dependencies {
    api("org.slf4j:slf4j-api:$slf4jVersion")
    api("io.grpc:grpc-api")
    api("io.projectreactor:reactor-core")
    api("io.projectreactor.kotlin:reactor-kotlin-extensions")
}
