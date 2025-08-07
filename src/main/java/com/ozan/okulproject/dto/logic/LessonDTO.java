package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozan.okulproject.dto.users.TeacherDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonDTO {
    @JsonIgnore
    private Long id;

    @NotNull(message = "Please enter lesson name")
    @Size(min = 2, max = 16, message = "Lesson name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Lesson name must consist of")
    private String lessonName;

    @NotNull(message = "Please enter lesson code")
    @Size(min = 2, max = 16, message = "Lesson code should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Lesson code must consist of")
    private String lessonCode;

    @NotNull(message = "Please enter lesson credit")
    private Integer creditScore;

    @NotNull(message = "Please enter isCompulsory")
    private Boolean isMandatory;

    private TeacherDTO teacher;
}
