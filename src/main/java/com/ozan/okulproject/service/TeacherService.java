package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
import com.ozan.okulproject.dto.users.TeacherQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;

import java.util.List;

public interface TeacherService {
    List<UserDTO> getAllTeacherDetails();

    List<TeacherQuickListDTO> getTeacherQuickList();

    List<TeacherQuickListDTO> getAdvisorQuickList();

    List<String> getLessonUnassignedTeachers();

    TeacherDetailsDTO assignAdvisor(Long teacherId);

    TeacherDetailsDTO unassignAdvisor(Long teacherId);

}
