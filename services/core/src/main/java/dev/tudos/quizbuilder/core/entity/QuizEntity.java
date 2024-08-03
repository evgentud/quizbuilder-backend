package dev.tudos.quizbuilder.core.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quiz")
@Getter
@Setter
public class QuizEntity extends BaseEntity {
    @Column(name = "owner_uuid", nullable = false)
    private String ownerUuid;

    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private QuizType type;

    @ManyToOne
    @JoinColumn(name = "category_uuid", nullable = false)
    private QuizCategoryEntity category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "quiz_quiz_tag",
            joinColumns = @JoinColumn(name = "quiz_uuid"),
            inverseJoinColumns = @JoinColumn(name = "tag_uuid")
    )
    private Collection<QuizTagEntity> tags;

    @OneToMany(mappedBy = "quiz",  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QuizQuestionEntity> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(ownerUuid, that.ownerUuid)
                && Objects.equals(title, that.title)
                && type == that.type
                && Objects.equals(category.getUuid(), that.getCategory().getUuid())
                && Objects.equals(tags, that.tags)
                && Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownerUuid, title, type, category.getUuid(), tags, questions);
    }
}
