import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	id("org.springframework.boot") version "2.5.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.github.ben-manes.versions") version "0.39.0"
}

group = "uk.marwatsmill"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_12

repositories {
	mavenCentral()
}

object Versions {
	const val kotest = "4.6.0"
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

	// Http
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Logging
	implementation("io.github.microutils:kotlin-logging:2.0.8")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotest}")
	testImplementation("io.kotest:kotest-assertions-core:${Versions.kotest}")
	testImplementation("io.kotest:kotest-property:${Versions.kotest}")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "12"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
