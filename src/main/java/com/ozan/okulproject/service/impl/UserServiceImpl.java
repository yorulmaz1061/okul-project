package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.entity.StudentDetails;
import com.ozan.okulproject.entity.TeacherDetails;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.UserRepository;
//import com.ozan.okulproject.service.KeycloakService;
import com.ozan.okulproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //private final KeycloakService keycloakService;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO save(UserDTO dto) {
        dto.setEnabled(true);

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = mapperUtil.convert(dto, User.class);
        user.setPassword(encodedPassword);

        if (Objects.requireNonNull(user.getRole()) == Role.TEACHER) {
            user.setStudentDetails(null);
            if (user.getTeacherDetails() == null) {
                TeacherDetails teacherDetails = new TeacherDetails();
                teacherDetails.setIsAdvisor(false);
                user.setTeacherDetails(teacherDetails);
            } else if (user.getTeacherDetails().getIsAdvisor() == null) {
                user.getTeacherDetails().setIsAdvisor(false);
            }
        } else {
            user.setTeacherDetails(null);
            user.setStudentDetails(null);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new OkulProjectException("Email already exists: " + dto.getEmail());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new OkulProjectException("Username already exists: " + dto.getUsername());
        }
        if (userRepository.existsBySsn(dto.getSsn())) {
            throw new OkulProjectException("SSN already exists: " + dto.getSsn());
        }


        User savedUser = userRepository.save(user);
        return mapperUtil.convert(savedUser, UserDTO.class);
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstName(false);
        return userList.stream().map(user -> mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());

    }

    @Override
    public List<UserDTO> getAllByRole(Role role) {
        List<User> userList = userRepository.findAllByRoleAndIsDeleted(role, false);
        return userList.stream().map(user -> mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        return mapperUtil.convert(user, UserDTO.class);
    }

    @Override
    public UserDTO deleteUserById(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        user.setIsDeleted(true);
        user.setEnabled(false);
        userRepository.save(user);
        return mapperUtil.convert(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) throws OkulProjectException {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        if (user == null) {
            throw new OkulProjectException("User not found with id: " + id);
        }
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }
        if (!user.getUsername().equals(dto.getUsername()) || !user.getSsn().equals(dto.getSsn())
                || !user.getGender().equals(dto.getGender()) || !user.getRole().equals(dto.getRole())
                || !user.getDateOfBirth().equals(dto.getDateOfBirth()) || !user.getBirthPlace().equals(dto.getBirthPlace())
                || !user.getMotherName().equals(dto.getMotherName()) || !user.getFatherName().equals(dto.getFatherName())
                || !user.isEnabled() == dto.isEnabled()) {
            throw new OkulProjectException("This field/fields cannot be changed");
        }

        if (user.getRole() == Role.TEACHER && dto.getTeacherDetailsDTO() != null) {
            Boolean requestedIsAdvisor = dto.getTeacherDetailsDTO().getIsAdvisor();
            if (requestedIsAdvisor != null) {
                if (user.getTeacherDetails() == null) {
                    user.setTeacherDetails(new TeacherDetails());
                }
                Boolean currentIsAdvisor = user.getTeacherDetails().getIsAdvisor();
                if (Objects.equals(currentIsAdvisor, requestedIsAdvisor)) {
                    if (requestedIsAdvisor) {
                        throw new OkulProjectException("This teacher is already assigned as an advisor");
                    } else {
                        throw new OkulProjectException("This teacher is already unassigned as an advisor");
                    }
                }
                user.getTeacherDetails().setIsAdvisor(requestedIsAdvisor);
            }
        } else if (dto.getTeacherDetailsDTO() != null && dto.getTeacherDetailsDTO().getIsAdvisor() != null) {
            throw new OkulProjectException("Advisor status can only be updated for teachers");
        }
        User savedUser = userRepository.save(user);
        return mapperUtil.convert(savedUser, UserDTO.class);
    }



}









