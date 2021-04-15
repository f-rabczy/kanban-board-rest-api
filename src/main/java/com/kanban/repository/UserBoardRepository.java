package com.kanban.repository;

import com.kanban.entity.userboard.UserBoard;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    @NotNull Optional<UserBoard> findById(@NotNull Long id);

}
