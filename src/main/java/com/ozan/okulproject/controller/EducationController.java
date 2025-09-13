package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.logic.EducationTermDTO;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.service.EducationTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
@Tag(name = "LessonEducationCombinedController", description = "Lesson Education Combined API")
public class EducationController {
    private final EducationTermService educationTermService;

    public EducationController(EducationTermService educationTermService) {
        this.educationTermService = educationTermService;
    }

    @ExecutionTime
    @PostMapping
    // @RolesAllowed("Admin")
    @Operation(summary = "Create Education Term")
    public ResponseEntity<ResponseWrapper> createEducationTerm(@RequestBody EducationTermDTO dto) {
        educationTermService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Education Term is successfully created", dto, HttpStatus.CREATED));
    }

    @ExecutionTime
    @GetMapping("/{id}")
    @Operation(summary = "Get Education Term By Id")
    public ResponseEntity<ResponseWrapper> getAllEducationTerm(@PathVariable("id") Long id) throws OkulProjectException {
        EducationTermDTO educationTermDTO = educationTermService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Education Term is successfully retrieved", educationTermDTO, HttpStatus.OK));

    }

    @ExecutionTime
    @GetMapping
    @Operation(summary = " Get All Education Terms")
    public ResponseEntity<ResponseWrapper> getAllEducationTerms() {
        List<EducationTermDTO> dtoList = educationTermService.listAllEducationTerms();
        return ResponseEntity.ok(new ResponseWrapper("All education terms are successfully retrieved", dtoList, HttpStatus.OK));
    }

    @ExecutionTime
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Education Term By Id")
    public ResponseEntity<ResponseWrapper> deleteEducationTermById(@PathVariable("id") Long id) {
        EducationTermDTO educationTermDTO = educationTermService.deleteEducationTermById(id);
        return ResponseEntity.ok(new ResponseWrapper("Education Term is successfully deleted", educationTermDTO, HttpStatus.OK));
    }

    @ExecutionTime
    @PutMapping("/{id}")
    @Operation(summary = "Update Education Term")
    public ResponseEntity<ResponseWrapper> updateEducationTerm(@PathVariable("id") Long id, @RequestBody EducationTermDTO dto) throws OkulProjectException {
        EducationTermDTO educationTermDTO = educationTermService.updateEducationTerm(id, dto);
        return ResponseEntity.ok(new ResponseWrapper("Education Term is successfully updated", educationTermDTO, HttpStatus.OK));
    }

}
