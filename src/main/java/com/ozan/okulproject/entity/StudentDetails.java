package com.ozan.okulproject.entity;

import com.ozan.okulproject.entity.logic.Meet;
import com.ozan.okulproject.entity.logic.StudentLessonInfo;
import com.ozan.okulproject.enums.Score;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetails {

    private Integer totalAbsence;

    private Double totalMidtermExamsAverageGrade;

    private Double totalFinalExamsAverageGrade;

    private Double totalTermAverageGrade;

    @Enumerated(EnumType.STRING)
    private Score totalTermLetterScore;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    List<StudentLessonInfo> studentLessonInfos;

    @ManyToMany(mappedBy = "studentList")
    List<Meet> meetsForStudent = new ArrayList<>();


}
