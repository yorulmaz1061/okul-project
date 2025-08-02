package com.ozan.okulproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.enums.Term;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "education_terms")

public class EducationTerm extends BaseEntity {

    @NotNull(message = "Education term must be provided.")
    @Enumerated(EnumType.STRING)
    private Term term;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    @NotNull(message = "Term start date must be provided.")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    @NotNull(message = "Term end date must be provided.")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "last_registration_date")
    @NotNull(message = "Term last registration date must be provided.")
    private LocalDate lastRegistrationDate;

    @OneToMany(mappedBy = "educationTerm", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LessonProgram> lessonPrograms;
}
