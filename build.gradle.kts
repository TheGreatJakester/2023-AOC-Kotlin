plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
}


val ktorVersion = "2.1.3"

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-encoding:$ktorVersion")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
