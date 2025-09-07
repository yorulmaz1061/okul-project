package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.users.TeacherQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.service.TeacherService;
import com.ozan.okulproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@Tag(name = "UserController",description = "Teacher API")
public class TeacherController {
    private final TeacherService teacherService;


    public TeacherController(TeacherService teacherService, UserService userService) {
        this.teacherService = teacherService;
    }

    @ExecutionTime
    @GetMapping
    // @RolesAllowed("Admin")
    @Operation(summary = "Get All Teachers Details")
    public ResponseEntity<ResponseWrapper> getAllTeacherDetails(){
        List<UserDTO> dtoList = teacherService.getAllTeacherDetails();
        return ResponseEntity.ok(new ResponseWrapper("All teachers details retrieved", dtoList, HttpStatus.OK));

    }
    @ExecutionTime
    @GetMapping("/list")
    // @RolesAllowed("Admin")
    @Operation(summary = "Get Teacher Quick List")
    public ResponseEntity<ResponseWrapper> getTeacherQuickList(){
        List<TeacherQuickListDTO> dtoList = teacherService.getTeacherQuickList();
        return ResponseEntity.ok(new ResponseWrapper("All teachers quick list retrieved", dtoList, HttpStatus.OK));
    }
    @ExecutionTime
    @GetMapping("/list-advisors")
    // @RolesAllowed("Admin")
    @Operation(summary = "Get Advisor Quick List")
    public ResponseEntity<ResponseWrapper> getAdvisorQuickList(){
        List<TeacherQuickListDTO> dtoList = teacherService.getAdvisorQuickList();
        return ResponseEntity.ok(new ResponseWrapper("All advisors quick list retrieved", dtoList, HttpStatus.OK));
    }





}
