package com.ozan.okulproject.entity;

import com.ozan.okulproject.enums.Role;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="user_roles")
public class UserRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private Role role;

    private String roleName;
}

