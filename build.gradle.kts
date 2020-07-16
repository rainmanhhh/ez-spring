plugins {
  val v = object {
    val kotlin = "1.3.72"
  }
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  id("org.jetbrains.dokka") version "0.10.1"
  kotlin("jvm") version v.kotlin
  kotlin("plugin.spring") version v.kotlin
  kotlin("kapt") version v.kotlin
  idea
  maven
}

group = "com.github.rainmanhhh"
version = "0.0.15"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

repositories {
  mavenLocal()
  maven("https://maven.aliyun.com/repository/public")
  jcenter()
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.boot:spring-boot-dependencies:2.3.1.RELEASE")
  }
}

dependencies {
  val v = object {
    val shiro = "1.4.2"
    val springDoc = "1.4.3"
  }
  api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  api("org.jooq:jooq")
  api("org.apache.shiro:shiro-spring:${v.shiro}")
  api("org.springframework:spring-context")
  api("org.springframework.data:spring-data-commons")
  api("org.springdoc:springdoc-openapi-common:${v.springDoc}")
  api("jakarta.servlet:jakarta.servlet-api")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks {
  val sourceJar by creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
  }

  val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/dokka"
  }

  val javadocJar by creating(Jar::class) {
//    dependsOn(dokka)
    group = "jar"
    archiveClassifier.set("javadoc")
    from(dokka.outputDirectory)
  }
  artifacts {
    add("archives", sourceJar)
    add("archives", javadocJar)
//    archives jar
//      archives sourceJar
//      archives generateJavadoc
  }
}