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

        importOrder()
        removeUnusedImports()

        eclipse()
        formatAnnotations()

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

        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")
    }
}
