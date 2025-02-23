package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Integer> {

}
