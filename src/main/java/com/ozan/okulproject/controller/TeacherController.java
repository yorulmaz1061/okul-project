package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.users.TeacherDetailsDTO;
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
        List<TeacherDetailsDTO> dtoList = teacherService.getAllTeacherDetails();
        return ResponseEntity.ok(new ResponseWrapper("All teachers details retrieved", dtoList, HttpStatus.OK));

    }
}
