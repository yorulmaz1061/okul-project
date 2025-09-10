package com.ozan.okulproject.repository;

import com.ozan.okulproject.entity.logic.StudentLessonInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentLessonInfoRepository extends JpaRepository<StudentLessonInfo, Long> {

    boolean existsByLesson_IdAndStudent_IdAndIsDeletedFalse(Long lessonId, Long studentId);

    long countByLesson_IdAndIsDeletedFalse(Long lessonId);

    Optional<StudentLessonInfo> findByLesson_IdAndStudent_IdAndIsDeletedFalse(Long lessonId, Long studentId);

}
