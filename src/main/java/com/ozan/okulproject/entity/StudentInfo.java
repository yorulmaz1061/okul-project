package com.ozan.okulproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozan.okulproject.enums.Score;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_infos")
public class StudentInfo extends BaseEntity{

    private Integer absence;

    private Double midtermGrade;

    private Double finalGrade;

    private Double averageGrade;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Score letterScore;

    @ManyToOne
    @JsonIgnore
    private User teacher;

    @ManyToOne
    @JsonIgnore
    private User student;

    @ManyToOne
    private Lesson lesson;

    @OneToOne
    private EducationTerm educationTerm;
}
