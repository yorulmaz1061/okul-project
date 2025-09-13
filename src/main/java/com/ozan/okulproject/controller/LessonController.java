package com.ozan.okulproject.controller;

import com.ozan.okulproject.annotation.ExecutionTime;
import com.ozan.okulproject.dto.logic.AssignTeacherRequest;
import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.LessonScheduleDTO;
import com.ozan.okulproject.dto.logic.StudentLessonInfoDTO;
import com.ozan.okulproject.entity.ResponseWrapper;
import com.ozan.okulproject.entity.logic.Lesson;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.repository.LessonRepository;
import com.ozan.okulproject.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lessons")
@Tag(name = "LessonController", description = "Lesson API")
public class LessonController {
    private final LessonService lessonService;
    private final LessonRepository lessonRepository;

    public LessonController(LessonService lessonService, LessonRepository lessonRepository) {
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
    }

    @ExecutionTime
    @GetMapping("/{id}")
    @Operation(summary = "Get Lesson By Id")
    public ResponseEntity<ResponseWrapper> getLessonById(@PathVariable("id") Long id) throws OkulProjectException {
        LessonDTO lessonDTO = lessonService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Lesson is successfully retrieved",lessonDTO, HttpStatus.OK));
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
    public ResponseEntity<ResponseWrapper> createLesson(@RequestBody @Valid LessonDTO dto){
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
    @PutMapping("/{lessonId}/teacher")
    @Operation(summary = "Assign Teacher To Lesson")
    public ResponseEntity<ResponseWrapper> assignTeacherToLesson(@PathVariable("lessonId") Long lessonId,
            @Valid @RequestBody AssignTeacherRequest request) {
        LessonDTO updated = lessonService.assignTeacherToLesson(lessonId, request.teacherId());
        return ResponseEntity
                .ok(new ResponseWrapper("Teacher is assigned to lesson", updated, HttpStatus.OK));
    }

    @ExecutionTime
    @PostMapping("/schedule")
    @Operation(summary = "Create Lesson Schedule")
    public ResponseEntity<ResponseWrapper> createLessonSchedule(@RequestBody @Valid LessonScheduleDTO dto){
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
    @ExecutionTime
    @DeleteMapping("/schedule/{lessonId}")
    @Operation(summary = "Delete Lesson Schedule By Lesson Id")
    public ResponseEntity<ResponseWrapper> deleteLessonScheduleByLessonId(@PathVariable("lessonId")Long lessonId){
       LessonScheduleDTO lessonScheduleDTO =  lessonService.deleteLessonScheduleByLessonId(lessonId);
       return ResponseEntity.ok(new ResponseWrapper("Lesson Schedule is successfully deleted",lessonScheduleDTO, HttpStatus.OK));
    }

    @ExecutionTime
    @PostMapping("/{lessonId}/students/{studentId}")
    @Operation(summary = "Enroll a student to lesson")
    public ResponseEntity<ResponseWrapper> enrollStudentToLesson(@PathVariable("lessonId") Long lessonId,
                                                                 @PathVariable("studentId") Long studentId){
    StudentLessonInfoDTO infoDTO = lessonService.enrollStudent(lessonId,studentId);
    return ResponseEntity.ok(new ResponseWrapper("Student is successfully enrolled to lesson",infoDTO, HttpStatus.OK));
    }

    @ExecutionTime
    @DeleteMapping("/{lessonId}/students/{studentId}")
    @Operation(summary = "Unenroll a student to lesson")
    public ResponseEntity<ResponseWrapper>unenrollStudentToLesson(@PathVariable("lessonId") Long lessonId,
                                                                  @PathVariable("studentId") Long studentId){
        lessonService.unenrollStudentFromLesson(lessonId,studentId);
        return ResponseEntity.noContent().build();
    }

    @ExecutionTime
    @PutMapping("/{lessonId}/students/{studentId}/grade")
    @Operation(summary = "Grade a student")
    public ResponseEntity<ResponseWrapper> updateStudentGrade(
            @PathVariable Long lessonId,
            @PathVariable Long studentId,
            @Valid @RequestBody StudentLessonInfoDTO dto) {

        StudentLessonInfoDTO result = lessonService.gradeStudent(lessonId, studentId, dto);
        return ResponseEntity.ok(new ResponseWrapper("Student is successfully graded", result, HttpStatus.OK));
    }




}