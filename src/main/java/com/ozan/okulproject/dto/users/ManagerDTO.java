package com.ozan.okulproject.dto.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ManagerDTO extends UserDTO{
    private String formattedManagerNumber;

}
