package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AskRepository extends JpaRepository<Ask,String> {

    @Query("SELECT a FROM Ask a WHERE a.product.id = :productId AND a.size = :size AND a.active=true ORDER BY a.amount ASC LIMIT 1")
    Optional<Ask> getLowestAskForASizeOfAProduct(@Param("productId") String id, @Param("size") Size size);

    @Query(value = "SELECT amount from asks WHERE size= :size AND product_id= :id AND active=true ORDER BY amount ASC limit 1",nativeQuery = true)
    Integer getLowestAskAmount(@Param("productId") String productId, @Param("size") String size);

}
