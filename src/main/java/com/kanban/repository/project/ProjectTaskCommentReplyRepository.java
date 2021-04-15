package com.kanban.repository.project;

import com.kanban.entity.project.ProjectTaskCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskCommentReplyRepository extends JpaRepository<ProjectTaskCommentReply,Long> {
}
