package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
import com.ozan.okulproject.dto.users.TeacherQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.TeacherDetails;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.UserRepository;
import com.ozan.okulproject.service.TeacherService;
import com.ozan.okulproject.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final UserService userService;
    private final TeacherService teacherService;
    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;
    //private final KeycloakService keycloakService;

    public TeacherServiceImpl(UserService userService, @Lazy TeacherService teacherService, MapperUtil mapperUtil, UserRepository userRepository) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDTO> getAllTeacherDetails() {
        List<User> allTeacherWithDetails = userRepository.findAllTeacherWithDetails();
        return allTeacherWithDetails.stream()
                .map(user -> mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());

    }

    @Override
    public List<TeacherQuickListDTO> getTeacherQuickList() {
        List<UserDTO> allTeacherDetails = teacherService.getAllTeacherDetails();
        return allTeacherDetails.stream()
                .map(userDTO -> TeacherQuickListDTO.builder()
                        .id(userDTO.getId())
                        .username(userDTO.getUsername())
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .email(userDTO.getEmail())
                        .isAdvisor(userDTO.getTeacherDetailsDTO().getIsAdvisor())
                        .build()).toList();
    }

    @Override
    public List<TeacherQuickListDTO> getAdvisorQuickList() {
        List<TeacherQuickListDTO> teacherQuickList = teacherService.getTeacherQuickList();
        List<TeacherQuickListDTO> list = teacherQuickList.stream().filter(teacher -> teacher.getIsAdvisor() == true)
                .toList();
        if (list.isEmpty()) {
            throw new OkulProjectException("There is no advisor in the teacher list");
        }
        return list;
    }

    @Override
    public List<String> getLessonUnassignedTeachers() {
      return teacherService.getAllTeacherDetails()
                .stream().filter(teacher -> teacher.getTeacherDetailsDTO().getLessons().isEmpty())
                .map(teacher -> teacher.getId() + " - " + teacher.getFirstName() + " - " + teacher.getLastName())
                .toList();

    }

    @Override

    public TeacherDetailsDTO assignAdvisor(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new OkulProjectException("Teacher not found with id: " + teacherId));
        if (teacher.getRole() != Role.TEACHER) {
            throw new OkulProjectException("Only users with TEACHER role can be assigned as advisor. userId=" + teacherId);
        }
        if (!teacher.isEnabled()) {
            throw new OkulProjectException("Teacher account is disabled. userId=" + teacherId);
        }
        if (teacher.getTeacherDetails() == null) {
            teacher.setTeacherDetails(new TeacherDetails());
        }
        TeacherDetails details = teacher.getTeacherDetails();
        if (Boolean.TRUE.equals(details.getIsAdvisor())) {
            throw new OkulProjectException("Teacher is already assigned as advisor. userId=" + teacherId);
        }
        details.setIsAdvisor(true);
        userRepository.save(teacher);
        return TeacherDetailsDTO.builder().isAdvisor(true).build();
    }

    @Override
    public TeacherDetailsDTO unassignAdvisor(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new OkulProjectException("Teacher not found with id: " + teacherId));
        if (teacher.getRole() != Role.TEACHER) {
            throw new OkulProjectException("Only users with TEACHER role can be unassigned from advisor role. userId=" + teacherId);
        }
        if (!teacher.isEnabled()) {
            throw new OkulProjectException("Teacher account is disabled. userId=" + teacherId);
        }
        if (teacher.getTeacherDetails() == null || !Boolean.TRUE.equals(teacher.getTeacherDetails().getIsAdvisor())) {
            throw new OkulProjectException("Teacher is not an advisor already. userId=" + teacherId);
        }
        teacher.getTeacherDetails().setIsAdvisor(false);
        userRepository.save(teacher);
        return TeacherDetailsDTO.builder().isAdvisor(false).build();
    }


}


