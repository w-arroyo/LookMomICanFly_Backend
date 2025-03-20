package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,String> {

    Optional<Color> findByName(String name);

    @Query(value = "INSERT INTO colors_products VALUES (':productId',':colorId')", nativeQuery = true)
    void insertProductColors(@Param("productId") String productId, @Param("colorId") String colorId);

}
