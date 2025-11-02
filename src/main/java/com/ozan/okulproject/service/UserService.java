package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO dto);

    List<UserDTO> listAllUsers();

    List<UserDTO> getAllByRole(Role role);

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO dto) throws OkulProjectException;

    UserDTO deleteUserById(Long id);

    UserDTO getUserByUsername(String username);

}

