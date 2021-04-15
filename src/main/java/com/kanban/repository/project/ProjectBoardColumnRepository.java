package com.kanban.repository.project;

import com.kanban.entity.project.ProjectBoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectBoardColumnRepository extends JpaRepository<ProjectBoardColumn,Long> {
}
