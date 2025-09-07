package com.ozan.okulproject.repository;

import com.ozan.okulproject.entity.logic.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Lesson findByIsDeletedAndId(boolean b, Long id);

    List<Lesson> findAllByIsDeleted(boolean b);
}
