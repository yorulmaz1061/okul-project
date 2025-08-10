package com.ozan.okulproject.entity;

import com.ozan.okulproject.enums.Role;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) return null;
        return role.getValue(); // store "Admin", "Student", etc.
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (Role role : Role.values()) {
            if (role.getValue().equalsIgnoreCase(dbData)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role from db: " + dbData);
    }
}
