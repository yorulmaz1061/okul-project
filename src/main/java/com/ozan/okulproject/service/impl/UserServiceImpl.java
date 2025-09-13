package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.entity.TeacherDetails;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.UserRepository;
import com.ozan.okulproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        if (user == null) {throw new OkulProjectException("User not found with id: " + id);}
        return mapperUtil.convert(user, UserDTO.class);
    }

    @Override
    public UserDTO deleteUserById(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        if (user == null) {throw new OkulProjectException("User not found with id: " + id);}
        user.setIsDeleted(true);
        user.setEnabled(false);
        userRepository.save(user);
        return mapperUtil.convert(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.getUsersByUsername(username);
        if (user == null) throw new OkulProjectException("User not found with username: " + username);
        return mapperUtil.convert(user, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findByIdAndIsDeleted(id, false);
        if (user == null) {
            throw new OkulProjectException("User not found with id: " + id);
        }
        // ---------- IMMUTABLE GUARD ----------
        if (dto.getSsn() != null && !dto.getSsn().equals(user.getSsn())) {
            throw new OkulProjectException("SSN cannot be changed");
        }
        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().equals(user.getDateOfBirth())) {
            throw new OkulProjectException("Date of birth cannot be changed");
        }
        if (dto.getBirthPlace() != null && !dto.getBirthPlace().equals(user.getBirthPlace())) {
            throw new OkulProjectException("Birth place cannot be changed");
        }
        if (dto.getGender() != null && dto.getGender() != user.getGender()) {
            throw new OkulProjectException("Gender cannot be changed");
        }
        if (dto.getRole() != null && dto.getRole() != user.getRole()) {
            throw new OkulProjectException("Role cannot be changed");
        }
        // NOT: enabled READ_ONLY olduğundan DTO'dan gelmez; ayrıca guard gerekmez.

        // ---------- MUTABLE FIELDS ----------
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName().trim());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName().trim());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber().trim());
        }
        if (dto.getMotherName() != null) {
            user.setMotherName(dto.getMotherName().trim());
        }
        if (dto.getFatherName() !=null){
            user.setFatherName(dto.getFatherName().trim());
        }
        // Username: mutable + uniqueness check
        if (dto.getUsername() != null) {
            String newUsername = dto.getUsername().trim();
            String currentUsername = user.getUsername();
            if (!newUsername.equals(currentUsername)) {
                if (userRepository.existsByUsername(newUsername)) {
                    throw new OkulProjectException("Username already exists: " + newUsername);
                }
                user.setUsername(newUsername);
            }
        }
        if (dto.getEmail() != null) {
            String newEmail = dto.getEmail().trim().toLowerCase();
            String currentEmail = user.getEmail().toLowerCase();
            if (!newEmail.equals(currentEmail)) {
                if (userRepository.existsByEmail(newEmail)) {
                    throw new OkulProjectException("Email already exists: " + dto.getEmail());
                }
                user.setEmail(newEmail);
            }
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        User saved = userRepository.save(user);
        return mapperUtil.convert(saved, UserDTO.class);
    }





}









