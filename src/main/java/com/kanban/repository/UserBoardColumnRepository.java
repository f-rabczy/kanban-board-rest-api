package com.kanban.repository;

import com.kanban.entity.userboard.UserBoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardColumnRepository extends JpaRepository<UserBoardColumn,Long> {

}
