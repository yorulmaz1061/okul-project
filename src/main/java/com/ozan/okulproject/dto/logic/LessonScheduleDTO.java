package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.okulproject.enums.Day;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LessonScheduleDTO {

    @NotNull(message = "Please enter Day/Days")
    private List<Day> dayList;

    @NotNull(message = "Please enter start time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Please enter start time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    private LessonDTO lesson;

}
