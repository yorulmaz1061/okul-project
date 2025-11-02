package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.enums.Gender;
import com.ozan.okulproject.enums.Role;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Please enter username.")
    @Size(min = 2, max = 20, message = "Your username should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters.")
    private String username;

    @NotNull(message = "Please enter SSN number")
    @Pattern(
            regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "Please enter valid SSN number"
    )
    private String ssn;

    @NotNull(message = "Please enter first name.")
    @Size(min = 2, max = 20, message = "Your name should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters.")
    private String firstName;

    @NotNull(message = "Please enter lastname")
    @Size(min = 2, max = 20, message = "Surname should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Surname must consist of the characters.")
    private String lastName;

    @NotNull(message = "Please enter birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private LocalDate dateOfBirth;

    @NotNull(message = "Please enter birthplace")
    @Size(min = 2, max = 20, message = "Your birthplace should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your birthplace must consist of the characters.")
    private String birthPlace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Please enter password")
    @Size(min = 8, max = 60, message = "Password should be {min}-{max} characters long.")
    private String password;

    @NotNull(message = "Please enter phone number")
    @Size(min = 12, max = 12, message = "Your phone number should be 12 characters long")
    @Pattern(
            regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number"
    )
    private String phoneNumber;

    @NotNull(message = "Please enter email")
    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 50, message = "Your email should be between 5 and 50 chars")
    private String email;

    @NotNull(message = "Please enter your mother name")
    @Size(min = 2, max = 16, message = "Your mother name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your mother name must consist of characters a-z and 0-9 ")
    private String motherName;

    @NotNull(message = "Please enter your father name")
    @Size(min = 2, max = 20, message = "Your father name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your father name must consist of characters a-z and 0-9 ")
    private String fatherName;

    @NotNull(message = "Please enter gender")
    private Gender gender;

    @NotNull(message = "Please enter user role")
    private Role role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled;

    private TeacherDetailsDTO teacherDetailsDTO;
    private StudentDetailsDTO studentDetailsDTO;

}




