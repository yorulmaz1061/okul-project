package com.ozan.okulproject.entity.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.okulproject.entity.BaseEntity;
import com.ozan.okulproject.entity.user.Student;
import com.ozan.okulproject.entity.user.Teacher;
import com.ozan.okulproject.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meets")
public class Meet extends BaseEntity {

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @ManyToMany
    @JoinTable(
            name = "meet_student",
            joinColumns = @JoinColumn(name = "meet_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
