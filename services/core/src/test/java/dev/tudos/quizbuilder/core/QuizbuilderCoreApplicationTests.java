package dev.tudos.quizbuilder.core;

import dev.tudos.quizbuilder.core.service.AbstractContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(AbstractContainerBaseTest.class)
class QuizbuilderCoreApplicationTests {
	@Test
	void contextLoads() {
	}
}
