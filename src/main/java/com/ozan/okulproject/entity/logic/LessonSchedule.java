package com.ozan.okulproject.entity.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.okulproject.entity.BaseEntity;
import com.ozan.okulproject.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lesson_schedules")
public class LessonSchedule extends BaseEntity {

    @ElementCollection(targetClass = Day.class)
    @CollectionTable(name = "lesson_schedule_days", joinColumns = @JoinColumn(name = "lesson_schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private List<Day> dayList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

}
