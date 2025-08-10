package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.okulproject.dto.logic.MeetDTO;
import com.ozan.okulproject.dto.logic.StudentLessonInfoDTO;
import com.ozan.okulproject.enums.Score;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDetailsDTO{

    private Integer totalAbsence;

    private Double totalMidtermExamsAverageGrade;

    private Double totalFinalExamsAverageGrade;

    private Double totalTermAverageGrade;

    private Score totalTermLetterScore;

    List<StudentLessonInfoDTO> studentLessonInfos;

    @JsonIgnore
    List<MeetDTO> meetsForStudent;

}
