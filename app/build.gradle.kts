plugins {
    id("java")
    id ("application")
    id ("com.github.ben-manes.versions") version "0.51.0"
    id ("checkstyle")
    id("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

import org.gradle.api.tasks.JavaExec

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
    // Javalin и шаблоны JTE
    implementation("io.javalin:javalin:5.6.3")
    implementation("io.javalin:javalin-rendering:5.6.3")
    implementation("gg.jte:jte:3.1.9")
    implementation("gg.jte:jte-runtime:1.15.1")

    // SLF4J
    implementation("org.slf4j:slf4j-simple:2.0.16")

    // База данных
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.postgresql:postgresql:42.7.3")

    // Тестирование
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("io.javalin:javalin-testtools:5.6.1")
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    testImplementation("com.squareup.okhttp3:okhttp-urlconnection:4.11.0")
    testImplementation ("com.konghq:unirest-java:3.13.6")
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

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

application {
    mainClass.set("hexlet.code.App")
}
