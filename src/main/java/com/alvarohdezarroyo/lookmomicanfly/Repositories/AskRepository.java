package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AskRepository extends JpaRepository<Ask,String> {

    @Query("SELECT a FROM Ask a WHERE a.product.id = :productId AND a.size = :size ORDER BY a.amount ASC LIMIT 1")
    Optional<Ask> getLowestAskForASizeOfAProduct(@Param("id") String id, @Param("size") Size size);

}
