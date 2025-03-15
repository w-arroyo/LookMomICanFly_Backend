package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.ShippingOption;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ShippingOptionRepository;
import org.springframework.stereotype.Service;

@Service
public class ShippingOptionService {

    private final ShippingOptionRepository shippingOptionRepository;

    public ShippingOptionService(ShippingOptionRepository shippingOptionRepository) {
        this.shippingOptionRepository = shippingOptionRepository;
    }

    public ShippingOption getShippingOptionById(String id){
        return shippingOptionRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Shipping Option Id does not exist.")
        );
    }

}
