import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val v = DepVersions
    idea
    maven
    kotlin("jvm") version v.kotlin
    id("org.jetbrains.kotlin.plugin.spring") version v.kotlin
    id("io.spring.dependency-management") version v.springDepManage
}

group = "ez"
version = "0.0.3"

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

val sourceJar by tasks.creating(Jar::class) {
    classifier = "sources"
    val main by sourceSets
    from(main.allSource)
}

val javadoc by tasks
val javadocJar by tasks.creating(Jar::class) {
    classifier = "javadoc"
    from(javadoc)
}

artifacts {
    val configName = "archives"
    add(configName, sourceJar)
    add(configName, javadocJar)
}