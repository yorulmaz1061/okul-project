package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.enums.Score;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentLessonInfoDTO {
    @JsonIgnore
    private Long id;

    @Min(0)
    private Integer absence;

    @Min(0) @Max(100)
    private Double midtermExamGrade;

    @Min(0) @Max(100)
    private Double finalExamGrade;

    @JsonProperty(access =  JsonProperty.Access.READ_ONLY)
    private Double termGrade;

    private String infoNote;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Score gradeLetterScore;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isPassed;

    private LessonDTO lesson;

    @JsonIgnoreProperties({
            "username","ssn","dateOfBirth","birthPlace","phoneNumber","email",
            "motherName","fatherName","gender","role","enabled",
            "teacherDetailsDTO","studentDetailsDTO","password"
    })
    private UserDTO student;

}
