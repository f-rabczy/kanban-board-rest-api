package com.kanban.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kanban.entity.user.UserDAO;


@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

	UserDAO findByUsername(String username);

}