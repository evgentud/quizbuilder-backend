package dev.tudos.quizbuilder.core.service;

import dev.tudos.quizbuilder.core.base.AbstractContainerBaseTest;
import dev.tudos.quizbuilder.core.entity.QuizAnswerEntity;
import dev.tudos.quizbuilder.core.entity.QuizCategoryEntity;
import dev.tudos.quizbuilder.core.entity.QuizEntity;
import dev.tudos.quizbuilder.core.entity.QuizQuestionEntity;
import dev.tudos.quizbuilder.core.entity.QuizType;
import dev.tudos.quizbuilder.core.repository.QuizCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: refactor, add prepare data utils
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(AbstractContainerBaseTest.class)
@ActiveProfiles("noauth")
class QuizServiceIntegrationTest {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizCategoryRepository quizCategoryRepository;

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    @Test
    void createFlashcardQuizWithSingleAnswerShouldNotThrowsException() {
        // given
        String ownerUuid = UUID.randomUUID().toString();

        QuizCategoryEntity quizCategory = new QuizCategoryEntity();
        quizCategory.setName("Other");
        quizCategory = quizCategoryRepository.save(quizCategory);

        QuizEntity quiz = new QuizEntity();
        quiz.setOwnerUuid(ownerUuid);
        quiz.setType(QuizType.FLASH_CARD);
        quiz.setTitle("Country Capitals Quiz");
        quiz.setCategory(quizCategory);

        QuizQuestionEntity question1 = new QuizQuestionEntity();
        question1.setQuiz(quiz);
        question1.setQuestion("What is the capital of France?");
        QuizAnswerEntity answer1 = new QuizAnswerEntity();
        answer1.setQuiz(quiz);
        answer1.setQuestion(question1);
        answer1.setAnswer("Paris");
        question1.setAnswers(List.of(answer1));
        question1.setCorrectAnswer(answer1);

        quiz.setQuestions(List.of(question1));

        // when
        QuizEntity createResult = assertDoesNotThrow(() -> quizService.createQuiz(quiz));
        Optional<QuizEntity> findResult = quizService.getByUuid(createResult.getUuid());

        // then
        assertTrue(findResult.isPresent(), "The stored quiz should be found.");
        QuizEntity resultEntity = findResult.get();
        assertEquals("Country Capitals Quiz", resultEntity.getTitle());
        assertEquals(QuizType.FLASH_CARD, resultEntity.getType());

        assertNotNull(resultEntity.getCategory());
        assertEquals("Other", resultEntity.getCategory().getName());

        assertEquals(1, resultEntity.getQuestions().size());
        assertEquals(1, resultEntity.getQuestions().get(0).getAnswers().size());
        assertEquals("What is the capital of France?", resultEntity.getQuestions().get(0).getQuestion());

        assertEquals("Paris", resultEntity.getQuestions().get(0).getAnswers().get(0).getAnswer());
        assertEquals("Paris", resultEntity.getQuestions().get(0).getCorrectAnswer().getAnswer());
        assertEquals(resultEntity.getQuestions().get(0).getAnswers().get(0).getUuid(),
                resultEntity.getQuestions().get(0).getCorrectAnswer().getUuid(),
                "The correct answer and the only answer should be the same record");
    }

    @Test
    void createFlashcardQuizWithMultipleAnswersThrowsException() {
        String ownerUuid = UUID.randomUUID().toString();

        QuizEntity quiz = new QuizEntity();
        quiz.setOwnerUuid(ownerUuid);
        quiz.setType(QuizType.FLASH_CARD);

        QuizQuestionEntity question1 = new QuizQuestionEntity();
        question1.setQuiz(quiz);
        question1.setQuestion("What is the capital of France?");
        QuizAnswerEntity answer1 = new QuizAnswerEntity();
        answer1.setQuiz(quiz);
        answer1.setQuestion(question1);
        answer1.setAnswer("Paris");
        QuizAnswerEntity answer2 = new QuizAnswerEntity();
        answer2.setQuiz(quiz);
        answer2.setQuestion(question1);
        answer2.setAnswer("London");
        question1.setAnswers(List.of(answer1, answer2));
        question1.setCorrectAnswer(answer1);

        quiz.setQuestions(List.of(question1));

        assertThrows(IllegalArgumentException.class, () -> quizService.createQuiz(quiz));
    }
}