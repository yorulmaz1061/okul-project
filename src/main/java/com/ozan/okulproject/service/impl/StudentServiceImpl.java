package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.StudentDetailsDTO;
import com.ozan.okulproject.dto.users.StudentQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.UserRepository;
import com.ozan.okulproject.service.StudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;
    private final StudentService studentService;

    //private final KeycloakService keycloakService;
    public StudentServiceImpl(MapperUtil mapperUtil, UserRepository userRepository, @Lazy StudentService studentService) {
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.studentService = studentService;
    }

    @Override
    public List<UserDTO> getAllStudentDetails() {
        List<User> allTeacherWithDetails = userRepository.findAllStudentWithDetails();
        return allTeacherWithDetails.stream()
                .map(user -> mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<StudentQuickListDTO> getStudentQuickList() {
        List<UserDTO> allStudentDetails = studentService.getAllStudentDetails();

        return allStudentDetails.stream()
                .map(u -> {
                    StudentDetailsDTO sd = u.getStudentDetailsDTO();
                    if (sd == null) sd = new StudentDetailsDTO();
                    return StudentQuickListDTO.builder()
                            .id(u.getId())
                            .username(u.getUsername())
                            .firstName(u.getFirstName())
                            .lastName(u.getLastName())
                            .phoneNumber(u.getPhoneNumber())
                            .email(u.getEmail())
                            .totalAbsence(sd.getTotalAbsence())
                            .totalMidtermExamsAverageGrade(sd.getTotalMidtermExamsAverageGrade())
                            .totalFinalExamsAverageGrade(sd.getTotalFinalExamsAverageGrade())
                            .totalTermAverageGrade(sd.getTotalTermAverageGrade())
                            .totalTermLetterScore(sd.getTotalTermLetterScore())
                            .build();
                })
                .toList();
    }

}
