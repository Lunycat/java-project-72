plugins {
    application
    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.6"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.2.220")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-simple:2.0.9")
    // https://mvnrepository.com/artifact/io.javalin/javalin
    implementation("io.javalin:javalin:6.3.0")
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:6.2.0")
    // https://mvnrepository.com/artifact/com.h2database/h2
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass = "hexlet.code.App"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}