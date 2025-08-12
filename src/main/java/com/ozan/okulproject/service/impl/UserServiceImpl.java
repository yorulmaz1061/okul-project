package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.*;
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
import java.util.Map;
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

        User savedUser = userRepository.save(user);

        //keycloakService.userCreate(dto);

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
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        User convertedUser = mapperUtil.convert(dto, User.class);
        convertedUser.setId(user.getId());
        convertedUser.setStudentDetails(user.getStudentDetails());
        convertedUser.setTeacherDetails(user.getTeacherDetails());
        userRepository.save(convertedUser);
        return mapperUtil.convert(convertedUser, UserDTO.class);
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
    public UserDTO updateAdvisorStatus(Long id, Map<String, Boolean> updates) throws OkulProjectException {
        Boolean requestedStatus = updates.get("isAdvisor");
        if (requestedStatus == null) {
            throw new OkulProjectException("isAdvisor field is required");
        }

        User user = userRepository.findByIdAndIsDeleted(id, false);
        if (user == null) {
            throw new OkulProjectException("User not found with id: " + id);
        }
        if (!Role.TEACHER.equals(user.getRole())) {
            throw new OkulProjectException("Advisor status can only be updated for teachers");
        }

        Boolean currentStatus = user.getTeacherDetails().getIsAdvisor();

        if (Objects.equals(currentStatus, requestedStatus)) {
            if (requestedStatus) {
                throw new OkulProjectException("This teacher is already assigned as an advisor");
            } else {
                throw new OkulProjectException("This teacher is already unassigned as an advisor");
            }
        }

        // Update only if different
        user.getTeacherDetails().setIsAdvisor(requestedStatus);
        User savedUser = userRepository.save(user);
        return mapperUtil.convert(savedUser, new UserDTO());
    }


}









