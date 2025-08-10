package com.ozan.okulproject.entity;

import com.ozan.okulproject.enums.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) return null;
        return gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equalsIgnoreCase(dbData)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown gender from db: " + dbData);
    }
}
