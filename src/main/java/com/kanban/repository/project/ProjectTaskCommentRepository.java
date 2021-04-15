package com.kanban.repository.project;

import com.kanban.entity.project.ProjectTaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskCommentRepository extends JpaRepository<ProjectTaskComment,Long> {
}
