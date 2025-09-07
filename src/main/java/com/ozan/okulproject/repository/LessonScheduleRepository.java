package com.ozan.okulproject.repository;

import com.ozan.okulproject.entity.logic.LessonSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonScheduleRepository extends JpaRepository<LessonSchedule, Long> {
    List<LessonSchedule> findAllByLesson_IdAndIsDeletedFalse(Long lessonId);
    List<LessonSchedule> findAllByLesson_Teacher_IdAndIsDeletedFalse(Long teacherId);

}
