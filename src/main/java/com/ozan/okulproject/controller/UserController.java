package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.users.*;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.enums.Role;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody @Valid UserDTO dto){
        UserDTO saved = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created",saved, HttpStatus.CREATED));
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
    @GetMapping({"role/{role}"})
    //@RolesAllowed("Admin")
    @Operation(summary = "Get Users By Role")
    public ResponseEntity<ResponseWrapper> getUsersByRole(@PathVariable("role") Role role){
        List<UserDTO> userDTOList = userService.getAllByRole(role);
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved",userDTOList, HttpStatus.FOUND));
    }
    @ExecutionTime
    @GetMapping({"id/{id}"})
    //@RolesAllowed("Admin")
    @Operation(summary = "Get User By Id")
    public ResponseEntity<ResponseWrapper> getUserById(@PathVariable("id") Long id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved",userDTO, HttpStatus.FOUND));
    }
    @ExecutionTime
    @DeleteMapping({"id/{id}"})
    //@RolesAllowed("Admin")
    @Operation(summary = "Delete User By Id")
    public ResponseEntity<ResponseWrapper> deleteUserById(@PathVariable("id") Long id){
        UserDTO userDTO = userService.deleteUserById(id);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",userDTO, HttpStatus.OK));

    }
    @ExecutionTime
    @PutMapping(value = {"id/{id}"})
    //@RolesAllowed("Admin")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO dto) throws OkulProjectException {
        UserDTO userDTO = userService.updateUser(id, dto);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated",userDTO,HttpStatus.OK));

    }








    

















}
