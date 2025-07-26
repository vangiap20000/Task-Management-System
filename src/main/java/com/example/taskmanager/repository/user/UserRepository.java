package com.example.taskmanager.repository.user;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("legacyUserRepository")
public interface UserRepository extends JpaRepository<User, Long>, BaseRepository<User, Long>, UserRepositoryCustom {

}
