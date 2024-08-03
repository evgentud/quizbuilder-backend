package dev.tudos.quizbuilder.core.service.impl;

import dev.tudos.quizbuilder.core.entity.QuizEntity;
import dev.tudos.quizbuilder.core.entity.QuizType;
import dev.tudos.quizbuilder.core.repository.QuizRepository;
import dev.tudos.quizbuilder.core.service.QuizService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Optional<QuizEntity> getByUuid(UUID id) {
        return quizRepository.findById(id);
    }

    @Override
    public QuizEntity createQuiz(QuizEntity quiz) {
        if (quiz.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (quiz.getType().equals(QuizType.FLASH_CARD)
                && quiz.getQuestions().stream().anyMatch(q -> q.getAnswers().size() != 1)) {
            throw new IllegalArgumentException("Quiz question with type \"Flash Card\" should only have one answer.");
        }

        return quizRepository.save(quiz);
    }
}
