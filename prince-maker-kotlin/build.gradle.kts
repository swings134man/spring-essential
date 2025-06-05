import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val springBootVersion = "3.2.9"
	val kotlinVersion = "1.9.23"	// 3.2.9 버전과 호환되는 kotlin 버전 (꼭 호환성 체크 필요함)
	java

	id("org.springframework.boot") version springBootVersion
	id("io.spring.dependency-management") version "1.1.4"

	kotlin("jvm") version kotlinVersion 			// kotlin jvm 플러그인
	kotlin("plugin.spring") version kotlinVersion 	// kotlin spring 호환성(open class) 플러그인
	kotlin("kapt") version kotlinVersion			// Kotlin Annotation Processing Tool
	kotlin("plugin.jpa") version kotlinVersion 		// kotlin jpa 호환성(entity class에 매개변수 없는 기본 생성자 생성)
}

group = "com.makers"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

// kotlin
kapt {
	keepJavacAnnotationProcessors = true
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-validation")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Querydsl - test 예정
//	val querydslVersion = "5.0.0"
//	implementation("com.querydsl:querydsl-jpa:$querydslVersion")
//	kapt("com.querydsl:querydsl-jpa:$querydslVersion")

	runtimeOnly ("com.h2database:h2")
	compileOnly ("org.projectlombok:lombok")
	annotationProcessor ("org.projectlombok:lombok")

	testImplementation ("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.8") // MockK
	testImplementation("io.kotest:kotest-runner-junit5:5.8.0") // koTest
}