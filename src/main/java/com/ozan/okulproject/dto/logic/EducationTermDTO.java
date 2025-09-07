package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.enums.Term;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EducationTermDTO {

    private Long id;

    @NotNull(message = "Please enter start date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Please enter end date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Please enter last registration date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastRegistrationDate;

    /**
     * Generated automatically from startDate.
     * Example: "2025-Spring Semester"
     * This field is READ-ONLY for clients.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String termLabel;

    @JsonIgnore
    private List<LessonDTO> lessons;
}

