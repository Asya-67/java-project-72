plugins {
    id("java")
    id ("application")
    id ("com.github.ben-manes.versions") version "0.51.0"
    id ("checkstyle")
    id("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
}
jacoco {
    toolVersion = ("0.8.11")
}

sonar {
    properties {
        property ("sonar.projectKey", "Asya-67_java-project-72")
        property ("sonar.organization", "asya-67-71")
        property ("sonar.host.url", "https://sonarcloud.io")
    }
}

checkstyle {
    toolVersion = ("10.26.1")
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
    isShowViolations = true
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("info.picocli:picocli:4.7.7")
    annotationProcessor ("info.picocli:picocli-codegen:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.0")
    testImplementation ("org.assertj:assertj-core:3.24.2")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

application {
    mainClass.set("hexlet.code.App")
}