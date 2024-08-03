package dev.tudos.quizbuilder.core.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "quiz_question")
@Getter
@Setter
public class QuizQuestionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "quiz_uuid", nullable = false)
    private QuizEntity quiz;

    @Column
    private String question;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "correct_answer_uuid", referencedColumnName = "uuid")
    private QuizAnswerEntity correctAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QuizAnswerEntity> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizQuestionEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getQuizUuid(), that.getQuizUuid())
                && Objects.equals(question, that.question)
                && Objects.equals(getCorrectAnswerUuid(), that.getCorrectAnswerUuid())
                && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quiz, question, correctAnswer, answers);
    }

    @Nullable
    public UUID getQuizUuid() {
        return quiz != null ? quiz.getUuid() : null;
    }

    @Nullable
    public UUID getCorrectAnswerUuid() {
        return correctAnswer != null ? correctAnswer.getUuid() : null;
    }
}
