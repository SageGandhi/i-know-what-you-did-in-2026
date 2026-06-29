plugins {
    id("java")
    id("idea")
    id("com.google.protobuf") version "0.10.0"

}

group = "edu.gandhi.prajit"
version = "1.0"

repositories {
    mavenCentral()
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.35.1"
    }
}
dependencies {
    implementation("com.google.protobuf:protobuf-java:4.35.1")
    implementation("com.google.protobuf:protobuf-java-util:4.35.1")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")
}

tasks.test {
    useJUnitPlatform()
}