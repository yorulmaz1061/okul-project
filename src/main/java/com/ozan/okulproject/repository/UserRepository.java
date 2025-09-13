package com.ozan.okulproject.repository;


import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByIdAndIsDeleted(Long id, Boolean isDeleted);

    List<User> findAllByRoleAndIsDeleted(Role role, Boolean isDeleted);

    User findByIdAndIsDeleted(Long id, boolean deleted);

    List<User> findAllByIsDeletedOrderByFirstName(boolean b);

    @Query("SELECT u FROM User u JOIN FETCH u.teacherDetails WHERE u.role = com.ozan.okulproject.enums.Role.TEACHER ")
    List<User> findAllTeacherWithDetails();

    @Query("SELECT u from User u JOIN FETCH u.studentDetails WHERE u.role =com.ozan.okulproject.enums.Role.STUDENT ")
    List<User> findAllStudentWithDetails();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    User getUsersByUsername(String username);
}
