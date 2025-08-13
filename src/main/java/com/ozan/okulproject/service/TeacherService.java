package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.users.TeacherDetailsDTO;

import java.util.List;

public interface TeacherService {
    List<TeacherDetailsDTO> getAllTeacherDetails();
}
