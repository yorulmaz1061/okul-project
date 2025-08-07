package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.okulproject.dto.users.StudentDTO;
import com.ozan.okulproject.dto.users.TeacherDTO;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetDTO {

    private Long id;

    @NotNull(message = "Please enter meeting description")
    @Size(min = 2, max = 250, message = "Meeting description should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your meeting description must consist of characters a-z and 0-9 ")
    private String description;

    @NotNull(message = "Please enter meeting date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Future(message = "Meeting date must be in the future")
    private LocalDate date;

    @NotNull(message = "Please enter meeting start time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Please enter meeting end time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull(message = "Please select student/students")
    private List<StudentDTO> studentDTOList;

    @NotNull(message = "Please select advisor teacher")
    private TeacherDTO teacher;



}
