package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.enums.Role;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO dto);

    List<UserDTO> listAllUsers();


    List<UserDTO> getAllByRole(Role role);

}

