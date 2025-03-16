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

    @Modifying
    @Transactional
    @Query(value = "UPDATE selling_fees SET by_default=false WHERE by_default=true", nativeQuery = true)
    void deactivateCurrentSellingFeeOffers();

    @Query(value = "SELECT count(*) from transactions where date >= (NOW() - INTERVAL 3 MONTH) AND ask_id IN (SELECT id from asks WHERE user_id =: id)", nativeQuery = true)
    int getUserSalesDuringPastThreeMonths(@Param("id") String id);

    @Query(value = "SELECT * from fees WHERE by_default=true desc limit 1",nativeQuery = true)
    Optional<SellingFee> getDefaultSellingFee();

    @Modifying
    @Transactional
    @Query(value = "UPDATE asks SET active=false WHERE fee_id= :id", nativeQuery = true)
    int deactivateAsksWithASpecificFeeId(@Param("id") String id);

}
