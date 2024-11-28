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
    // https://mvnrepository.com/artifact/com.h2database/h2
    implementation("com.h2database:h2:2.2.220")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-simple:2.0.9")
    // https://mvnrepository.com/artifact/io.javalin/javalin
    implementation("io.javalin:javalin:6.3.0")
    // https://mvnrepository.com/artifact/io.javalin/javalin-bundle
    implementation("io.javalin:javalin-bundle:6.3.0")
    // https://mvnrepository.com/artifact/io.javalin/javalin-rendering
    implementation("io.javalin:javalin-rendering:6.3.0")
    // https://mvnrepository.com/artifact/gg.jte/jte
    implementation("gg.jte:jte:3.1.15")
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:6.2.0")
    // https://mvnrepository.com/artifact/com.konghq/unirest-java
    implementation("com.konghq:unirest-java:3.14.5")
    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.18.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
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