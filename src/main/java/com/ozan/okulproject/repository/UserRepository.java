package com.ozan.okulproject.repository;


import com.ozan.okulproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByIsDeletedOrderByFirstName(boolean deleted);


}
