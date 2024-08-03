package dev.tudos.quizbuilder.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "quiz_tag")
@Data
@EqualsAndHashCode(callSuper = true, of = {"name"})
public class QuizTagEntity extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<QuizEntity> quizzes;
}
