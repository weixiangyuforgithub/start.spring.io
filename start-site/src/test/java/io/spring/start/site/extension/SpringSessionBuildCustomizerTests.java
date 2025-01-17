/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.start.site.extension;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringSessionBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringSessionBuildCustomizerTests extends AbstractExtensionTests {

	private static final Dependency REDIS = Dependency.withId("session-data-redis", "org.springframework.session",
			"spring-session-data-redis");

	private static final Dependency JDBC = Dependency.withId("session-jdbc", "org.springframework.session",
			"spring-session-jdbc");

	@Test
	void noSessionWithRedis() {
		ProjectRequest request = createProjectRequest("data-redis");
		request.setBootVersion("2.0.0.M3");
		generateMavenPom(request).hasSpringBootStarterDependency("data-redis").hasSpringBootStarterTest()
				.hasDependenciesCount(2);
	}

	@Test
	void sessionWithNoStore() {
		ProjectRequest request = createProjectRequest("session", "data-jpa");
		request.setBootVersion("2.0.0.M3");
		generateMavenPom(request).hasDependency("org.springframework.session", "spring-session-core")
				.hasSpringBootStarterDependency("data-jpa").hasSpringBootStarterTest().hasDependenciesCount(3);
	}

	@Test
	void sessionWithRedis() {
		ProjectRequest request = createProjectRequest("session", "data-redis");
		request.setBootVersion("2.0.0.M3");
		generateMavenPom(request).hasSpringBootStarterDependency("data-redis").hasSpringBootStarterTest()
				.hasDependency(REDIS).hasDependenciesCount(3);
	}

	@Test
	void sessionWithRedisReactive() {
		ProjectRequest request = createProjectRequest("session", "data-redis-reactive");
		request.setBootVersion("2.0.0.M7");
		generateMavenPom(request).hasSpringBootStarterDependency("data-redis-reactive").hasSpringBootStarterTest()
				.hasDependency(REDIS).hasDependenciesCount(3);
	}

	@Test
	void sessionWithJdbc() {
		ProjectRequest request = createProjectRequest("session", "jdbc");
		request.setBootVersion("2.0.0.M3");
		generateMavenPom(request).hasSpringBootStarterDependency("jdbc").hasSpringBootStarterTest().hasDependency(JDBC)
				.hasDependenciesCount(3);
	}

	@Test
	void sessionWithRedisAndJdbc() {
		ProjectRequest request = createProjectRequest("session", "data-redis", "jdbc");
		request.setBootVersion("2.0.0.M3");
		generateMavenPom(request).hasSpringBootStarterDependency("data-redis").hasSpringBootStarterDependency("jdbc")
				.hasSpringBootStarterTest().hasDependency(REDIS).hasDependency(JDBC).hasDependenciesCount(5);
	}

}
