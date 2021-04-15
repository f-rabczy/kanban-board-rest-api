package com.kanban.repository.project;

import com.kanban.entity.project.Project;
import com.kanban.entity.user.UserDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository  extends JpaRepository<Project,Long> {

    List<Project> findAllById(Long id);

    List<Project> findAllByUsersIsContaining(UserDAO userDAO);



    @NotNull Optional<Project> findById(@NotNull Long id);
}
