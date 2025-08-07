package com.ozan.okulproject.enums;

public enum Role {
    ADMIN("Admin"),
    MANAGER("Manager"),
    TEACHER("Teacher"),
    STUDENT("Student");

    public final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
