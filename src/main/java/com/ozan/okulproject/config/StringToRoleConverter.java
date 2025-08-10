package com.ozan.okulproject.config;

import com.ozan.okulproject.enums.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        for (Role role : Role.values()) {
            if (role.getValue().equalsIgnoreCase(source) || role.name().equalsIgnoreCase(source)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + source);
    }
}