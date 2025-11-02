package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.LessonScheduleDTO;
import com.ozan.okulproject.dto.logic.StudentLessonInfoDTO;
import com.ozan.okulproject.exception.OkulProjectException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface LessonService {

    LessonDTO findById(Long id) throws OkulProjectException;

    List<LessonDTO> listAllLessons();

    LessonDTO deleteById(Long id);

    LessonDTO save(LessonDTO dto) throws OkulProjectException;

    LessonDTO updateLesson(Long id, LessonDTO dto);

    void saveLessonSchedule(LessonScheduleDTO dto);

    LessonScheduleDTO findLessonScheduleById(Long id);

    LessonDTO assignTeacherToLesson(Long lessonId, Long teacherId);

    LessonScheduleDTO deleteLessonScheduleByLessonId(Long lessonId);

    StudentLessonInfoDTO enrollStudent(Long lessonId, Long studentId);

    void unenrollStudentFromLesson(Long lessonId, Long studentId);

    StudentLessonInfoDTO gradeStudent(Long lessonId, Long studentId, StudentLessonInfoDTO dto);

}
