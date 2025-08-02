package com.ozan.okulproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozan.okulproject.enums.Term;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EducationTermDTO {

    private Long id;

    @NotNull(message = "Term must be provided.")
    private Term term;

    @NotNull(message = "Please enter start date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Please enter end date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Please enter last registration date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastRegistrationDate;
}
