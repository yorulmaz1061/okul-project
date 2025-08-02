package com.ozan.okulproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.okulproject.enums.Score;
import com.ozan.okulproject.enums.Term;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentAcademicInfoDTO{

    private Long id;

    private UserDTO student;

    private Double midTermGrade;

    private Double finalGrade;

    private Integer absence;

    private String infoNote;

    private LessonDTO lessonName;

    private int creditScore;

    private boolean isMandatory;

    private Term educationTerm;

    private Double average;

    private Score letterScore;

}
