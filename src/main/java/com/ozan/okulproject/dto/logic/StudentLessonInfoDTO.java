package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozan.okulproject.dto.users.StudentDetailsDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentLessonInfoDTO {
    @JsonIgnore
    private Long id;

    private Integer absence;

    private Double midtermExamGrade;

    private Double finalExamGrade;

    private Double totalLessonGrade;

    private String infoNote;

    private String letterScore;

    private Boolean isPassed;

    private LessonDTO lesson;

    private StudentDetailsDTO student;

}
