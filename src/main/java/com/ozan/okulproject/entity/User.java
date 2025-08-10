package com.ozan.okulproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozan.okulproject.enums.Gender;
import com.ozan.okulproject.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String ssn;

    private String firstName;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String birthPlace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String motherName;

    private String fatherName;

    private boolean enabled;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(nullable = false)
    @Convert(converter = RoleConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Role role;

    // ===== Teacher-Specific Fields =====
    @Embedded
    private TeacherDetails teacherDetails;

    // ===== Student-Specific Fields =====
    @Embedded
    private StudentDetails studentDetails;

}
