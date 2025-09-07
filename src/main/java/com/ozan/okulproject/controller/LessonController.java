package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.LessonScheduleDTO;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lesson")
@Tag(name = "LessonController", description = "Lesson API")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @ExecutionTime
    @GetMapping("/{id}")
    @Operation(summary = "Get Lesson By Id")
    public ResponseEntity<ResponseWrapper> getLessonById(@PathVariable("id") Long id) throws OkulProjectException {
        LessonDTO lessonDTO = lessonService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Lesson is successfully retrieved",lessonDTO, HttpStatus.FOUND));
    }
    @ExecutionTime
    @GetMapping
    @Operation(summary = "Get All Lessons")
    public ResponseEntity<ResponseWrapper> getAllLessons(){
        List<LessonDTO> dtoList = lessonService.listAllLessons();
        return ResponseEntity.ok(new ResponseWrapper("All lessons are successfully retrieved",dtoList, HttpStatus.OK));

    }
    @ExecutionTime
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Lesson By Id")
    public ResponseEntity<ResponseWrapper> deleteLessonById(@PathVariable("id")Long id){
        LessonDTO lessonDTO = lessonService.deleteById(id);
        return ResponseEntity.ok(new ResponseWrapper("Lesson is successfully deleted",lessonDTO, HttpStatus.OK));
    }
    @ExecutionTime
    @PostMapping
    @Operation(summary = "Create Lesson")
    public ResponseEntity<ResponseWrapper> createLesson(@RequestBody LessonDTO dto){
        LessonDTO saved = lessonService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Lesson is successfully created",saved,HttpStatus.CREATED));
    }
    @ExecutionTime
    @PutMapping("/{id}")
    @Operation(summary = "Update Lesson")
    public ResponseEntity<ResponseWrapper> updateLesson(@PathVariable("id") Long id, @RequestBody LessonDTO dto){
        LessonDTO lessonDTO = lessonService.updateLesson(id, dto);
        return ResponseEntity.ok(new ResponseWrapper("Lesson is successfully updated",lessonDTO, HttpStatus.OK));

    }
    @ExecutionTime
    @PostMapping("/schedule")
    @Operation(summary = "Create Lesson Schedule")
    public ResponseEntity<ResponseWrapper> createLessonSchedule(@RequestBody LessonScheduleDTO dto){
        lessonService.saveLessonSchedule(dto);
        return ResponseEntity.ok(new ResponseWrapper("Lesson Schedule is successfully created",dto, HttpStatus.OK));
    }
    @ExecutionTime
    @GetMapping("/schedule/{id}")
    @Operation(summary = "Get Lesson Schedule By Lesson Id")
    public ResponseEntity<ResponseWrapper> getLessonScheduleById(@PathVariable("id")Long id) {
        LessonScheduleDTO lessonServiceById = lessonService.findLessonScheduleById(id);
        return ResponseEntity.ok(new ResponseWrapper("Lesson Schedule is successfully retrieved",lessonServiceById, HttpStatus.OK));
    }


}
