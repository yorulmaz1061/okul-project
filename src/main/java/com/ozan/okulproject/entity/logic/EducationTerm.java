package com.ozan.okulproject.entity.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.entity.BaseEntity;
import com.ozan.okulproject.enums.Term;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "education_terms")
public class EducationTerm extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Term term;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "last_registration_date", nullable = false)
    private LocalDate lastRegistrationDate;

    @OneToMany(mappedBy = "educationTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @Column(name = "term_label", nullable = false)
    private String termLabel;

    public void calculateTermLabel() {
        if (startDate != null) {
            int year = startDate.getYear();
            int month = startDate.getMonthValue();

            String semester = (month == 1 || month == 2 || month == 3) ? "Spring Semester" :
                    (month == 8 || month == 9 || month == 10) ? "Fall Semester" : "Special Term";

            this.termLabel = year + "-" + semester;
        }

    }
}
