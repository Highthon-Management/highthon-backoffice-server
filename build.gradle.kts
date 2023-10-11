import com.ewerk.gradle.plugins.tasks.QuerydslCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("kapt") version "1.8.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {

    // db
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // orm
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    // cool sms
    implementation("net.nurigo:sdk:4.3.0")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // query dsl
    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

    // aws
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.232")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("org.springframework.data.redis.core.RedisHash")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("org.springframework.data.redis.core.RedisHash")
}