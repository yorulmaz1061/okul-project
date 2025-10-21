package com.ozan.okulproject.repository;

import com.ozan.okulproject.dto.users.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {
    Response userCreate(UserDTO userDTO);
    void delete(String userName);
}
