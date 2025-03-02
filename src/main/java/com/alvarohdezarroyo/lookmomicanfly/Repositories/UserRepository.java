package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET active=false WHERE email =:email", nativeQuery = true)
    int deactivateUserAccount(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password= :password WHERE email=:email", nativeQuery = true)
    int changeUserPassword(@Param("email") String email ,@Param("password") String password);

    @Modifying
    @Transactional
    @Query(value ="UPDATE users SET email = :newEmail WHERE email = :ogEmail", nativeQuery = true)
    int changeUserEmail(@Param("ogEmail") String ogEmail, @Param("newEmail") String newEmail);
}
