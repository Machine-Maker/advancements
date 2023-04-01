import io.papermc.paperweight.util.constants.*

plugins {
    `java-library`
    alias(libs.plugins.paperweight.userdev)
}

group="me.machinemaker.datapacks"
version="1.0-SNAPSHOT"

repositories {
    mavenLocal {
        mavenContent {
            includeGroup("me.machinemaker.machined-paper")
        }
    }
    // maven("https://repo.papermc.io/repository/maven-public/")
    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    compileOnly(libs.jb.annotations)
    compileOnly(libs.checker.qual)
    compileOnly(libs.machinedpaper.api)

    testImplementation(libs.adventure.nbt)
    testImplementation(libs.adventure.gson)
    testImplementation("org.hamcrest:hamcrest:2.2") // TODO remove

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.params)
    testRuntimeOnly(libs.junit.engine)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)

    paperweight.devBundle(libs.machinedpaper.devbundle)
}

configurations {
    mojangMappedServer {
        exclude("io.papermc.paper", "paper-api")
    }
}

java {
    configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME) { // prevent dev bundle from adding to compile classpath
        setExtendsFrom(extendsFrom.filter { it.name != MOJANG_MAPPED_SERVER_CONFIG })
    }
}


tasks {
    test {
        useJUnitPlatform()
        jvmArgs("-Djunit.jupiter.extensions.autodetection.enabled=true")
    }
}
