package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellingFeeRepository extends JpaRepository<SellingFee,String> {

    Optional<SellingFee> findByDescription(String s);
/*
    @Modifying
    @Transactional
    @Query(value = "UPDATE selling_fees SET by_default=false")
    int deactivateCurrentSellingFeeOffers();
    */
    @Query(value = "SELECT * from selling_fees WHERE by_default=true", nativeQuery = true)
    Optional<SellingFee> findByDefault();

    @Query(value = "SELECT count(*) from transactions where date >= (NOW() - INTERVAL 3 MONTH) AND ask_id IN (SELECT id from asks WHERE user_id =: id)", nativeQuery = true)
    Integer getUserSalesDuringPastThreeMonths(@Param("id") String id);

}
