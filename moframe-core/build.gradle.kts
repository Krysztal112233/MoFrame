plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "2.0.0+"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
}

tasks {
    runServer {
        minecraftVersion("1.21.5")
    }
}
