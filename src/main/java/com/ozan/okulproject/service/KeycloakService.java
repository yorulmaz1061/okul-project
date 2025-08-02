package com.ozan.okulproject.service;



import com.ozan.okulproject.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate( UserDTO userDTO);
    void delete(String username);
}
