package com.ozan.okulproject.entity.logic;

import com.ozan.okulproject.entity.BaseEntity;
import com.ozan.okulproject.entity.user.Student;
import com.ozan.okulproject.entity.user.Teacher;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    private String lessonName;
    private String lessonCode;
    private Integer creditScore;
    private Boolean isMandatory;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<LessonSchedule> lessonScheduleList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "education_term_id",nullable = false)
    EducationTerm educationTerm;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<StudentLessonInfo> studentLessonInfo = new ArrayList<>();

}
