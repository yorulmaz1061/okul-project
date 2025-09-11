package com.ozan.okulproject.repository;

import com.ozan.okulproject.entity.logic.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {
    @Query("SELECT m FROM Meet m WHERE m.teacher.id =: userId OR :userId IN (SELECT s.id FROM m.studentList s) ")
    List<Meet> findAllForUser(@Param("userId") Long userId);

    Meet findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
