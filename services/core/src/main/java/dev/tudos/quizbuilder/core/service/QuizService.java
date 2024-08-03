package dev.tudos.quizbuilder.core.service;

import dev.tudos.quizbuilder.core.entity.QuizEntity;

import java.util.Optional;
import java.util.UUID;

public interface QuizService {
    Optional<QuizEntity> getByUuid(UUID id);

    QuizEntity createQuiz(QuizEntity quiz);
}
