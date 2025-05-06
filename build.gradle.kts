import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.mycompany"
version = "0.0.1-beta0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

configurations.all {
	resolutionStrategy {
		force("org.jetbrains:annotations:23.0.0")
	}
}

dependencyLocking {
	lockAllConfigurations()
}

dependencies {
	constraints {
        implementation("org.jetbrains:annotations:23.0.0") {
            because("kotlinx-coroutines requires 23.0.0, kotlin-stdlib requires 13.0, resolving conflict")
        }
    }
		
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation(kotlin("reflect"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	
	// OpenAPI 3.0
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
	
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation(kotlin("test-junit5"))
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

// TODO: Consider how to separate and run unit tests and integration tests
tasks.withType<Test> {
	useJUnitPlatform()
	this.testLogging {
        this.showStandardStreams = true
        events("passed", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
