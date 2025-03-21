package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SellingFeeDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserLevel;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SellingFeeRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.SellingFeeMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellingFeeService {

    @Autowired
    private final SellingFeeRepository sellingFeeRepository;
    private final SaleService saleService;

    public SellingFeeService(SellingFeeRepository sellingFeeRepository, SaleService saleService) {
        this.sellingFeeRepository = sellingFeeRepository;
        this.saleService = saleService;
    }

    @Transactional
    public SellingFee saveSellingFeeOffer(SellingFeeDTO sellingFeeDTO){
        deactivateCurrentSellingFeeOffers();
        return sellingFeeRepository.save(SellingFeeMapper.toSellingFee(sellingFeeDTO));
    }

    public void deactivateCurrentSellingFeeOffers(){
        sellingFeeRepository.deactivateCurrentSellingFeeOffers();
    }

    public SellingFee getSellingFeeById(String id){
        return sellingFeeRepository.findById(id).orElseThrow(
                ()->   new EntityNotFoundException("Fee description does not exist.")
        );
    }

    public SellingFee getSellingFeeByDescription(String description){
        return sellingFeeRepository.findByDescription(description).orElseThrow(
                ()->   new EntityNotFoundException("Fee description does not exist.")
        );
    }

    public SellingFee checkIfThereIsADefaultFee(String userId){
        return sellingFeeRepository.findByByDefaultTrue().orElse(
                selectFeeByNumberSales(userId)
                );
    }

    public SellingFee selectFeeByNumberSales(String userId){
        final int sales=saleService.getUserNumberSalesDuringLastThreeMonths(userId);
        if(sales<3)
            return getSellingFeeByDescription(UserLevel.LEVEL_1.name());
        else if(sales<10)
            return getSellingFeeByDescription(UserLevel.LEVEL_2.name());
        else if(sales<50)
            return getSellingFeeByDescription(UserLevel.LEVEL_3.name());
        else return getSellingFeeByDescription(UserLevel.LEVEL_4.name());
    }

}
