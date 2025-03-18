package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid,String> {

    @Query("SELECT a FROM Bid a WHERE a.product.id = :productId AND a.size = :size ORDER BY a.amount DESC LIMIT 1")
    Optional<Bid> getHighestBid(@Param("id") String productId, @Param("size")Size size);

}
