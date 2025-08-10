package com.ozan.okulproject.repository;


import com.ozan.okulproject.entity.User;
import com.ozan.okulproject.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByIsDeletedOrderByFirstName(boolean deleted);

    List<User> findAllByIsDeletedOrderByFirstNameDesc(boolean b);


    List<User> findAllByRoleAndIsDeleted(Role role, Boolean isDeleted);
}
