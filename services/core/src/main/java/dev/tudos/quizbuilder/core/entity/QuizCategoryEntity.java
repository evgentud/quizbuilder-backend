package dev.tudos.quizbuilder.core.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "quiz_category")
@Getter
@Setter
public class QuizCategoryEntity extends BaseEntity {
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_uuid")
    private QuizCategoryEntity parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizCategoryEntity that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, that.name) && Objects.equals(getParentUuid(), that.getParentUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, getParentUuid());
    }

    @Nullable
    public UUID getParentUuid() {
        return parent != null ? parent.getUuid() : null;
    }
}
