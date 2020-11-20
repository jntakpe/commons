val testContainersVersion: String by project

dependencies {
    api(project(":commons-cache"))
    api(project(":commons-test"))
    api("org.testcontainers:testcontainers:$testContainersVersion")
}
