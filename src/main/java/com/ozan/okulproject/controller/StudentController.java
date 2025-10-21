package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.users.StudentQuickListDTO;
import com.ozan.okulproject.dto.users.UserDTO;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "StudentController",description = "Student API")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @ExecutionTime
    @GetMapping
    @RolesAllowed({"Admin","Teacher"})
    @Operation(summary = "Get All Students Details")
    public ResponseEntity<ResponseWrapper> getAllStudentDetails(){
        List<UserDTO> dtoList = studentService.getAllStudentDetails();
        return ResponseEntity.ok(new ResponseWrapper("All students details retrieved", dtoList, HttpStatus.OK));

    }
    @ExecutionTime
    @GetMapping("/list")
    @RolesAllowed({"Admin","Teacher"})
    @Operation(summary = "Get Student Quick List")
    public ResponseEntity<ResponseWrapper> getStudentQuickList(){
        List<StudentQuickListDTO> dtoList = studentService.getStudentQuickList();
        return ResponseEntity.ok(new ResponseWrapper("All students quick list retrieved", dtoList, HttpStatus.OK));
    }



}
