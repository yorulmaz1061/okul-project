package com.ozan.okulproject.dto.logic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.dto.users.UserDTO;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LessonDTO {
    private Long id;

    @NotNull(message = "Please enter lesson name")
    @Size(min = 2, max = 16, message = "Lesson name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Lesson name must consist of")
    private String lessonName;

    @NotNull(message = "Please enter lesson code")
    @Size(min = 2, max = 16, message = "Lesson code should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Lesson code must consist of")
    private String lessonCode;

    @NotNull(message = "Please enter lesson credit")
    private Integer creditScore;

    @NotNull(message = "Please enter isCompulsory")
    private Boolean isMandatory;

    @NotNull(message = "Please select education term id")
    private EducationTermDTO educationTermId;

    @JsonIgnoreProperties({
            "username","ssn","dateOfBirth","birthPlace","phoneNumber","email",
            "motherName","fatherName","gender","role","enabled",
            "teacherDetailsDTO","studentDetailsDTO"
    })
    private UserDTO teacherId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isTeacherAssigned;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String  assignedTeacherInformation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String termLabel;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String totalStudentsCounts;
}
