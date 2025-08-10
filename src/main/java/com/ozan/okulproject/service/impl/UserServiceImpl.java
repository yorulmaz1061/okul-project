package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.UserRepository;
//import com.ozan.okulproject.service.KeycloakService;
import com.ozan.okulproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return userList.stream().map(user->mapperUtil.convert(user,UserDTO.class)).collect(Collectors.toList());

    }

    @Override
    public List<UserDTO> getAllByRole(Role role) {
       List<User> userList = userRepository.findAllByRoleAndIsDeleted(role,false);
       return userList.stream().map(user->mapperUtil.convert(user,UserDTO.class)).collect(Collectors.toList());
    }


}






