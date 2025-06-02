plugins {
    java
    `java-library`
    id("com.diffplug.spotless") version "7.0.3"
    id("com.github.spotbugs") version "6.1.7"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
repositories {
    mavenCentral()
}

spotless {
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }

    java {
        target("**/*.java")
        targetExclude("**/package-info.java")

        importOrder()
        removeUnusedImports()

        eclipse()
        formatAnnotations()

        leadingTabsToSpaces(4)

        licenseHeaderFile("HEADERFILE")
    }
}

subprojects {
    apply {
        plugin<JavaPlugin>()
        plugin<JavaLibraryPlugin>()
    }

    repositories {
        mavenCentral()
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    dependencies {
        implementation("com.electronwill.night-config:toml:3.8.1")
        implementation("com.google.inject:guice:7.0.0")
        implementation("com.zaxxer:HikariCP:6.3.0")
        implementation("io.vavr:vavr:0.10.6")
        implementation("one.util:streamex:0.8.3")
        implementation("redis.clients:jedis:5.2.0")

        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")

        compileOnly("org.jetbrains:annotations:26.0.2")

        testImplementation(platform("org.junit:junit-bom:5.12.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.assertj:assertj-core:3.25.3")
    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
