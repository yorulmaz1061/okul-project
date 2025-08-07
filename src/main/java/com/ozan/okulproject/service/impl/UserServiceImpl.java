package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.user.User;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.UserRepository;
import com.ozan.okulproject.service.KeycloakService;
import com.ozan.okulproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, KeycloakService keycloakService, MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }


}

