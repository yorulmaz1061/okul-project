package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.logic.LessonDTO;
import com.ozan.okulproject.dto.logic.LessonScheduleDTO;
import com.ozan.okulproject.entity.logic.LessonSchedule;
import com.ozan.okulproject.exception.OkulProjectException;

import java.util.List;

public interface LessonService {

    LessonDTO findById(Long id) throws OkulProjectException;

    List<LessonDTO> listAllLessons();

    LessonDTO deleteById(Long id);

    LessonDTO save(LessonDTO dto) throws OkulProjectException;

    LessonDTO updateLesson(Long id, LessonDTO dto);

    void saveLessonSchedule(LessonScheduleDTO dto);

    LessonScheduleDTO findLessonScheduleById(Long id);

}
