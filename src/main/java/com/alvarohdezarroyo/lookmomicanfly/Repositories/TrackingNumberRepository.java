package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackingNumberRepository extends JpaRepository<TrackingNumber,String> {

    @Modifying
    @Query(value = "UPDATE tracking_numbers SET used=true where ID =: id", nativeQuery = true)
    int useTrackingNumber(@Param("id")String id);

    @Query(value = "INSERT INTO tracking_and_sales VALUES (':trackingId',':saleId')", nativeQuery = true)
    void insertIntoSalesTrackingTable(@Param("trackingId") String trackingId, @Param("saleId") String saleId);

    @Query(value = "INSERT INTO tracking_and_orders VALUES (':trackingId',':orderId')", nativeQuery = true)
    void insertIntoOrdersTrackingTable(@Param("trackingId") String trackingId, @Param("orderId") String orderId);

}
