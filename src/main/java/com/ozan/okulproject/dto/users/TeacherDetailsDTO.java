package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.MeetDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDetailsDTO {

    @NotNull(message = "Please enter if the teacher is advisor or not")
    private Boolean isAdvisor;

    @JsonIgnore
    private List<LessonDTO> lessons;

    @JsonIgnore
    private List<MeetDTO> meetsForTeacher;

}
