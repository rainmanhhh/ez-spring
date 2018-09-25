import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val v = DepVersions
    idea
    maven
    kotlin("jvm") version v.kotlin
    id("org.jetbrains.kotlin.plugin.spring") version v.kotlin
//    id("org.springframework.boot") version v.springboot
    id("io.spring.dependency-management") version v.springDepManage
}

group = "ez"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
}

val v = DepVersions
dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jooq:jooq:${v.jooq}")
    compile("org.apache.shiro:shiro-spring:${v.shiro}")
    compile("org.springframework.data:spring-data-commons:${v.springData}")
    compile("com.spring4all:swagger-spring-boot-starter:${v.swaggerStarter}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}