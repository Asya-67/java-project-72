import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    java
    jacoco
    checkstyle
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.sonarqube") version "6.2.0.5505"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.4.0")
    implementation("io.javalin:javalin-rendering:6.4.0")
    implementation("io.javalin:javalin-bundle:6.4.0")
    implementation("gg.jte:jte:3.1.9")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("com.konghq:unirest-java:3.13.6")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.postgresql:postgresql:42.7.3")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("io.javalin:javalin-testtools:6.4.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("com.konghq:unirest-java:3.13.6")
    testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showStandardStreams = true
    }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

checkstyle {
    toolVersion = "10.26.1"
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
    isShowViolations = true
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("all")
    mergeServiceFiles()
}

configurations.all {
    resolutionStrategy {
        force(
            "io.javalin:javalin:6.4.0",
            "io.javalin:javalin-rendering:6.4.0",
            "io.javalin:javalin-testtools:6.4.0"
        )
    }

    exclude(group = "ch.qos.logback", module = "logback-classic")
    exclude(group = "org.slf4j", module = "slf4j-log4j12")
}
