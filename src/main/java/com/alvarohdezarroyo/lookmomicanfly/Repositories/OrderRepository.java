package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET status= :status WHERE id= :orderId", nativeQuery = true)
    int changeOrderStatus(@Param("orderId") String orderId, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET status = 'DELIVERED' WHERE status IN ('SHIPPED')", nativeQuery = true)
    int completeShippedOrders();

}
