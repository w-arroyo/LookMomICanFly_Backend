package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.UserLevel;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SellingFeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellingFeeService {

    @Autowired
    private final SellingFeeRepository sellingFeeRepository;

    public SellingFeeService(SellingFeeRepository sellingFeeRepository) {
        this.sellingFeeRepository = sellingFeeRepository;
    }

    @Transactional
    public SellingFee saveSellingFeeOffer(SellingFee sellingFee){
        removeSellingFeeOffers();
        return sellingFeeRepository.save(sellingFee);
    }

    @Transactional
    public void removeSellingFeeOffers(){
        sellingFeeRepository.deactivateCurrentSellingFeeOffers();
    }

    public SellingFee getSellingFeeById(String id){
        return sellingFeeRepository.findById(id).orElseThrow(
                ()->   new EntityNotFoundException("Fee description does not exist.")
        );
    }

    public int returnUserNumberSalesDuringPastThreeMonths(String id){
        return sellingFeeRepository.getUserSalesDuringPastThreeMonths(id);
    }

    public SellingFee getSellingFeeByDescription(String description){
        return sellingFeeRepository.findByDescription(description).orElseThrow(
                ()->   new EntityNotFoundException("Fee description does not exist.")
        );
    }

    public SellingFee checkIfThereIsADefaultFee(String userId){
        return sellingFeeRepository.getDefaultSellingFee().orElse(
                selectFeeByNumberSales(returnUserNumberSalesDuringPastThreeMonths(userId))
                );
    }

    public SellingFee selectFeeByNumberSales(Integer sales){
        if(sales==null)
            throw new EntityNotFoundException("User id doesn't exist.");
        else if(sales<3)
            return getSellingFeeByDescription(UserLevel.LEVEL_1.name());
        else if(sales<10)
            return getSellingFeeByDescription(UserLevel.LEVEL_2.name());
        else if(sales<50)
            return getSellingFeeByDescription(UserLevel.LEVEL_3.name());
        else return getSellingFeeByDescription(UserLevel.LEVEL_4.name());
    }

    @Transactional
    public String deactivateAsksWithASpecificFeeId(String id){
        return ("Selling fee removed successfully. "+sellingFeeRepository.deactivateAsksWithASpecificFeeId(id)+" asks were deactivated.");
    }

}
