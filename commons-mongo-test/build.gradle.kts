val testContainersVersion: String by project

dependencies {
    api(project(":commons-mongo"))
    api(project(":commons-test"))
    api("org.testcontainers:mongodb:$testContainersVersion")
}
