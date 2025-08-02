package com.ozan.okulproject.dto;

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
    private Long id;

    @NotNull(message = "Please enter lesson name")
    @Size(min = 2, max = 16, message = "Lesson name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Lesson name must consist of")
    private String name;

    @NotNull(message = "Please enter credit score")
    private Integer creditScore;

    @NotNull(message = "Please enter isCompulsory")
    private Boolean isMandatory;
}
