import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.3.3.RELEASE"
  id("io.spring.dependency-management") version "1.0.10.RELEASE"
  kotlin("jvm") version "1.4.0"
  kotlin("plugin.spring") version "1.4.0"
  kotlin("plugin.jpa") version "1.4.0"
}

group = "com.vk.oed"
version = "1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  // Kotlin
  implementation(kotlin("reflect"))
  implementation(kotlin("stdlib-jdk8"))
  // Coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
  // Gson - JSON lib
  implementation("com.google.code.gson:gson:2.8.6")
  // Spring
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  // MySQL
  runtimeOnly("mysql:mysql-connector-java")
  // Task scheduler
  implementation("com.github.shyiko.skedule:skedule:0.4.0")

  // Discord
  implementation("net.dv8tion:JDA:4.2.0_192") {
    exclude(module = "opus-java")
  }
  implementation("dev.minn:jda-ktx:0.1.0")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks {
  register("fatJar", Jar::class.java) {
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
      attributes(
          "Main-Class" to "com.vk.oed.slaver.SlaverApplicationKt"
      )
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("add from dependencies: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
  }
}
