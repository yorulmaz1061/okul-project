package com.ozan.okulproject.entity;

import com.ozan.okulproject.entity.logic.EducationTerm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDateTime;

    @Column(nullable = false, updatable = false)
    private Long insertUserId;

    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;

    @Column(nullable = false)
    private Long lastUpdateUserId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @PrePersist
    public void onPrePersist() {
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();
        this.insertUserId = 1L;  // TODO: replace with authenticated user
        this.lastUpdateUserId = 1L;

        runEntitySpecificLogic();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L; // TODO: replace with authenticated user

        runEntitySpecificLogic();
    }

    private void runEntitySpecificLogic() {
        if (this instanceof EducationTerm educationTerm) {
            educationTerm.calculateTermLabel();
        }
        // If other entities need special pre-persist/update logic in the future,
        // add more instanceof checks here.
    }
}

