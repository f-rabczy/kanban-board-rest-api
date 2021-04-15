package com.kanban.repository.project;

import com.kanban.entity.project.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectBoardRepository extends JpaRepository<ProjectBoard,Long> {
}
