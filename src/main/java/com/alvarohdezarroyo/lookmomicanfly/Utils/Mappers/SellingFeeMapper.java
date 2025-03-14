package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SellingFeeDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.SellingFee;
import org.mapstruct.Mapper;

@Mapper
public class SellingFeeMapper {

    public static SellingFeeDTO toDTO(SellingFee sellingFee){
        return new SellingFeeDTO(sellingFee.getId(),sellingFee.getDescription(),sellingFee.getPercentage());
    }

    public static SellingFee toSellingFee(SellingFeeDTO sellingFeeDTO){
        final SellingFee sellingFee=new SellingFee();
        sellingFee.setByDefault(true);
        sellingFee.setDescription(sellingFeeDTO.getDescription());
        sellingFee.setPercentage(sellingFeeDTO.getPercentage());
        return sellingFee;
    }

}
