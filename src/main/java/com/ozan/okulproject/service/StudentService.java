package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.users.StudentQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;

import java.util.List;

public interface StudentService {
    List<UserDTO> getAllStudentDetails();

    List<StudentQuickListDTO> getStudentQuickList();
}
