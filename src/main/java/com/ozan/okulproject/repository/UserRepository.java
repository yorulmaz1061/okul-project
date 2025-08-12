package com.ozan.okulproject.repository;


import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByIdAndIsDeleted(Long id, Boolean isDeleted);

    List<User> findAllByRoleAndIsDeleted(Role role, Boolean isDeleted);

    User findByIdAndIsDeleted(Long id, boolean deleted);

    List<User> findAllByIsDeletedOrderByFirstName(boolean b);

}
