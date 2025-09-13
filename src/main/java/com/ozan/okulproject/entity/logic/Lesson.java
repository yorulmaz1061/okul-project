package com.ozan.okulproject.entity.logic;

import com.ozan.okulproject.entity.BaseEntity;
import com.ozan.okulproject.entity.User;
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
    private Boolean isTeacherAssigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacher_id",
            foreignKey = @ForeignKey(name = "fk_lessons_teacher"))
    //@JoinColumn(name = "teacher_id")
    //@org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private User teacher;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonSchedule> lessonScheduleList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_term_id",nullable = false)
    private EducationTerm educationTerm;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentLessonInfo> studentLessonInfo = new ArrayList<>();


}
