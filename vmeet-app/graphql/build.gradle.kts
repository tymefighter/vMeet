plugins {
    java
    id("org.springframework.boot") version "3.2.8"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.vmeet"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.leangen.graphql:graphql-spqr-spring-boot-starter:1.0.1")
    implementation("com.graphql-java:graphql-java:21.5")
    implementation(project(":data"))
    implementation(project(":authentication"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework:spring-webflux")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}