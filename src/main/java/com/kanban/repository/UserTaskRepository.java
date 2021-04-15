package com.kanban.repository;

import com.kanban.entity.userboard.UserTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserTaskRepository extends CrudRepository<UserTask, Long> {

    void deleteById(Long id);

}
