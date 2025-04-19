package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SellingFeeDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import org.springframework.stereotype.Component;

@Component
public class SellingFeeMapper {

    public static SellingFeeDTO toDTO(SellingFee sellingFee){
        return new SellingFeeDTO(sellingFee.getId(),sellingFee.getDescription().replace("_"," "),sellingFee.getPercentage(), sellingFee.getByDefault());
    }

    public static SellingFee toSellingFee(SellingFeeDTO sellingFeeDTO){
        final SellingFee sellingFee=new SellingFee();
        sellingFee.setByDefault(true);
        sellingFee.setDescription(sellingFeeDTO.getDescription());
        sellingFee.setPercentage(sellingFeeDTO.getPercentage());
        sellingFee.setByDefault(sellingFeeDTO.isByDefault());
        return sellingFee;
    }

}
