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
@Table(name = "student_infos")
public class StudentLessonInfo extends BaseEntity {

    private Integer absence;

    private Double midtermExamGrade;

    private Double finalExamGrade;

    private Double totalLessonGrade;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Score letterScore;

    private Boolean isPassed;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

}
