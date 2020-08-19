plugins {
  kotlin("jvm") version "1.4.0"
}

group = "com.vk.oed"
version = "0.0.0"
val kotlinVersion = "1.4.0"

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  // Coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

  // Discord
  implementation("net.dv8tion:JDA:4.2.0_192")
  // logger, required by JDA
  implementation("org.slf4j:slf4j-jdk14:1.7.30")


  // Tests
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}