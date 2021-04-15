package com.kanban.repository;

import com.kanban.entity.user.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture,Long> {

   Optional<ProfilePicture> findByName(String name);
}
