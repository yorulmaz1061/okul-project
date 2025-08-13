package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.service.TeacherService;
import com.ozan.okulproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final UserService userService;
    //private final KeycloakService keycloakService;
    private final MapperUtil mapperUtil;

    public TeacherServiceImpl(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<TeacherDetailsDTO> getAllTeacherDetails() {
        List<UserDTO> teacherDTOList = userService.listAllUsers().stream()
                .filter(userDTO -> userDTO.getRole().equals(Role.TEACHER))
                .toList();
        return teacherDTOList.stream().map(UserDTO::getTeacherDetailsDTO).toList();
    }
}
