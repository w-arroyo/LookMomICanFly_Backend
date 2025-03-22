package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,String> {

    @Query(value = "SELECT COUNT(*) FROM transactions WHERE sale_id IN (SELECT id FROM sales WHERE ask_id IN (SELECT id FROM posts WHERE user_id = :id AND finalized = TRUE) AND status = 'COMPLETED') AND date >= NOW() - INTERVAL 3 MONTH", nativeQuery = true)
    int getUserSalesDuringPastThreeMonths(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sales SET status= :status WHERE id= :saleId", nativeQuery = true)
    int changeSaleStatus(@Param("saleId") String orderId, @Param("status") String status);

    @Query("SELECT s FROM Sale s WHERE s.status NOT IN ('FAILED','COMPLETED','CANCELLED')")
    List<Sale> getAllOngoingSales();

}
