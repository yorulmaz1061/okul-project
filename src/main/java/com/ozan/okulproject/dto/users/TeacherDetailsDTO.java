package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.MeetDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeacherDetailsDTO {
    private Long id;
    private Boolean isAdvisor;
    @JsonIgnore
    private List<LessonDTO> lessons;
    @JsonIgnore
    private List<MeetDTO> meetsForTeacher;

}
