package com.ozan.okulproject.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeacherQuickListDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Boolean isAdvisor;

}
