package dev.tudos.quizbuilder.core.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "quiz_answer")
@Getter
@Setter
public class QuizAnswerEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "quiz_uuid", nullable = false)
    private QuizEntity quiz;

    @ManyToOne
    @JoinColumn(name = "quiz_question_uuid", nullable = false)
    private QuizQuestionEntity question;

    @Column
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizAnswerEntity)) return false;
        if (!super.equals(o)) return false;
        QuizAnswerEntity that = (QuizAnswerEntity) o;
        return Objects.equals(getUuid(), that.getUuid())
                && Objects.equals(getQuizUuid(quiz), getQuizUuid(that.quiz))
                && Objects.equals(getQuestionUuid(question), getQuestionUuid(that.question))
                && Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUuid(), answer, quiz != null ? quiz.getUuid() : 0,
                question != null ? question.getUuid() : null);
    }

    @Nullable
    public UUID getQuizUuid(QuizEntity quiz) {
        return quiz != null ? quiz.getUuid() : null;
    }

    @Nullable
    public UUID getQuestionUuid(QuizQuestionEntity question) {
        return question != null ? question.getUuid() : null;
    }
}
