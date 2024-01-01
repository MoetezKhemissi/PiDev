package com.example.Project.Repositories;

import com.example.Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//    @Query("select u.Password from User u where u.Email=?1")
//    public String getPasswordByUserEmail(String Email);

    @Query(" select u from User u " +
            " where u.Username = ?1")
    User findByUsername(String username);

    User  findByVerificationToken(String Token);

//    boolean existsByEmail(String Email);
//
//    User findUserByEmail(String Email);
//
    @Query(" select u from User u " +
        " where u.Phone = ?1")
    User findByPhone(Long Phone);

    User findByVerificationCode(String code);
}
