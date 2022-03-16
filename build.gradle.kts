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

dependencies {
    compileOnly("org.jetbrains:annotations:22.0.0")
    compileOnly("me.machinemaker.machined-paper:machinedpaper-api:1.18.1-R0.1-SNAPSHOT") {
        exclude(group = "junit", module = "junit")
    }

    testImplementation("net.kyori:adventure-nbt:4.9.3")
    testImplementation("me.machinemaker.machined-paper:machinedpaper-api:1.18.1-R0.1-SNAPSHOT") {
        exclude(group = "junit", module = "junit")
    }
    testImplementation("net.kyori:adventure-text-serializer-gson:4.9.3")
    testImplementation("org.mockito:mockito-core:4.2.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    test {
        useJUnitPlatform()
    }
}