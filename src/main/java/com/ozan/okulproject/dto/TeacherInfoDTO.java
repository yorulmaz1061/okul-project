package com.ozan.okulproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.units.qual.A;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherInfoDTO  {

    private Long id;

    private UserDTO teacher;

    private Set<LessonDTO> lessonDTOS;

    private Boolean isAdvisor;

}
