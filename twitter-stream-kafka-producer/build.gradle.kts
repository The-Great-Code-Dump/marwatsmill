import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
}

group = "uk.tojourn.twitter"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_12

repositories {
	mavenCentral()
}

object Versions {
	const val kotlintest = "4.6.0"
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.json:json:20210307")

	// Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

	// Http
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Logging
	implementation("io.github.microutils:kotlin-logging:2.0.6")

	// Metrics
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus:1.7.4")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotlintest}")
	testImplementation("io.kotest:kotest-assertions-core:${Versions.kotlintest}")
	testImplementation("io.kotest:kotest-property:${Versions.kotlintest}")
	annotationProcessor( "org.springframework.boot:spring-boot-configuration-processor")
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
