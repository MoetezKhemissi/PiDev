package com.example.Project.Repositories;

import com.example.Project.entities.User;
import com.example.Project.entities.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode,Long> {
    UserCode findByCode(String code);
}
