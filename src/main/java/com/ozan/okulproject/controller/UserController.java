package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "UserController",description = "User API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ExecutionTime
    @PostMapping
   // @RolesAllowed("Admin")
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO dto){
        userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created",HttpStatus.CREATED));
    }

    @ExecutionTime
    @GetMapping
    //@RolesAllowed("Admin")
    @Operation(summary = "Get All Users")
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved",userDTOList, HttpStatus.OK));
    }

    @ExecutionTime
    @GetMapping({"/{role}"})
    //@RolesAllowed("Admin")
    @Operation(summary = "Get All By Role")
    public ResponseEntity<ResponseWrapper> getUsersByRole(@PathVariable("role") Role role){
        List<UserDTO> userDTOList = userService.getAllByRole(role);
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved",userDTOList, HttpStatus.OK));
    }












}
