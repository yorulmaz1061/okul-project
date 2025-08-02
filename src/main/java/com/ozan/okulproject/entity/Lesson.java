package com.ozan.okulproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson extends BaseEntity{

    private String name;

    private Integer creditScore;

    private Boolean isMandatory;

    @ManyToMany(mappedBy = "lessons", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<LessonProgram> lessonPrograms;
}
