package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaleRepository extends JpaRepository<Sale,String> {

    @Query(value = "SELECT COUNT(*) FROM transactions WHERE sale_id IN (SELECT id FROM sales WHERE ask_id IN (SELECT id FROM posts WHERE user_id = :id AND finalized = TRUE) AND status = 'COMPLETED') AND date >= NOW() - INTERVAL 3 MONTH", nativeQuery = true)
    int getUserSalesDuringPastThreeMonths(@Param("id") String id);

}
