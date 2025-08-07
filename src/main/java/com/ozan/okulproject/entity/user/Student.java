package com.ozan.okulproject.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozan.okulproject.entity.logic.Meet;
import com.ozan.okulproject.entity.logic.StudentLessonInfo;
import com.ozan.okulproject.enums.Score;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
@DiscriminatorValue("STUDENT")
public class Student extends User{

    private String getFormattedStudentNumber() {
        return String.format("STU%05d", getId());
    }

    private Integer totalAbsence;

    private Double totalMidtermExamsAverageGrade;

    private Double totalFinalExamsAverageGrade;

    private Double totalTermAverageGrade;

    @Enumerated(EnumType.STRING)
    private Score totalTermLetterScore;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    List<StudentLessonInfo> studentLessonInfo;

    @ManyToMany(mappedBy = "studentList")
    List<Meet> meets = new ArrayList<>();

}
