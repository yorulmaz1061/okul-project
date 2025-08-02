package com.ozan.okulproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.okulproject.entity.EducationTerm;
import com.ozan.okulproject.enums.Day;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgramDTO {

    private Long id;

    @NotNull(message = "Please enter day")
    private Day day;

    @NotNull(message = "Please enter start time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Please enter end time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull(message = "Please select lesson")
    @Size(min = 1, message = "Please select at least one lesson")
    private Set<LessonDTO> lessons;

    @NotNull(message = "Please enter education term")
    private EducationTerm educationTerm;

    @NotNull(message = "Please enter education term")
    private Set<TeacherInfoDTO> teachers;

    @NotNull(message = "Please enter education term")
    private Set<StudentAcademicInfoDTO> students;
}
