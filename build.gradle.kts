plugins {
    `java-library`
}

group="me.machinemaker"
version="1.0-SNAPSHOT"

repositories {
    mavenLocal {
        mavenContent {
            includeGroup("me.machinemaker.machined-paper")
        }
    }
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("me.machinemaker.machined-paper:machinedpaper-api:1.18.2-R0.1-SNAPSHOT") {
        exclude(group = "junit", module = "junit")
    }
    // implementation("io.leangen.geantyref:geantyref:1.3.13")

    testImplementation("net.kyori:adventure-nbt:4.11.0")
    testImplementation("me.machinemaker.machined-paper:machinedpaper-api:1.18.2-R0.1-SNAPSHOT") {
        exclude(group = "junit", module = "junit")
    }
    testImplementation("net.kyori:adventure-text-serializer-gson:4.11.0")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    test {
        useJUnitPlatform()
        jvmArgs("-Djunit.jupiter.extensions.autodetection.enabled=true")
    }
}
