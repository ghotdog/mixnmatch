plugins {
	application
}

repositories {
	mavenCentral()
}

dependencies {
	// Use JUnit Jupiter for testing.
	testImplementation(libs.junit.jupiter)

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// This dependency is used by the application.
	implementation(libs.guava)
	// youtube api
	implementation("com.google.apis:google-api-services-youtube:v3-rev20240505-2.0.0")
	implementation("com.google.api-client:google-api-client:2.4.0")
	implementation("com.google.http-client:google-http-client-jackson2:1.34.0")
	implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.0")
	// https://mvnrepository.com/artifact/se.michaelthelin.spotify/spotify-web-api-java
	implementation("se.michaelthelin.spotify:spotify-web-api-java:9.0.0-RC1")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

application {
	mainClass = "mixnmatch.App"
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
