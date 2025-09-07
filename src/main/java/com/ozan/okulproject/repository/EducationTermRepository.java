package com.ozan.okulproject.repository;

import com.ozan.okulproject.entity.logic.EducationTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {
    EducationTerm findByIsDeletedAndId(boolean b, Long id);

    List<EducationTerm> findAllByIsDeleted(boolean b);

}
