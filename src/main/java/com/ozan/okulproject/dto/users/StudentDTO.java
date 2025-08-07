package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.okulproject.dto.logic.MeetDTO;
import com.ozan.okulproject.dto.logic.StudentLessonInfoDTO;
import com.ozan.okulproject.entity.logic.Meet;
import com.ozan.okulproject.entity.logic.StudentLessonInfo;
import com.ozan.okulproject.enums.Score;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO extends UserDTO {

    private String formattedStudentNumber;

    private Integer totalAbsence;

    private Double totalMidtermExamsAverageGrade;

    private Double totalFinalExamsAverageGrade;

    private Double totalTermAverageGrade;

    private Score totalTermLetterScore;

    List<StudentLessonInfoDTO> studentLessonInfos;

    @JsonIgnore
    List<MeetDTO> meets;

}
