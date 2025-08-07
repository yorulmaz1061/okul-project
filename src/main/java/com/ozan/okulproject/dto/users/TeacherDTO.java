package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.MeetDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDTO extends UserDTO {

    private String formattedTeacherNumber;
    @NotNull(message = "Please enter if the teacher is advisor or not")
    private Boolean isAdvisor;

    @JsonIgnore
    private List<LessonDTO> lessons;

    @JsonIgnore
    private List<MeetDTO> meets;

}
