package com.ozan.okulproject.entity.logic;

import com.ozan.okulproject.entity.BaseEntity;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Score;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_infos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"lesson_id","student_id","is_deleted"}))
public class StudentLessonInfo extends BaseEntity {

    private Integer absence;

    private Double midtermExamGrade;

    private Double finalExamGrade;

    private Double termGrade;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Score gradeLetterScore;

    private Boolean isPassed;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

}
