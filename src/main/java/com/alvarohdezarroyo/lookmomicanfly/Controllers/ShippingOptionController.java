package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ShippingOptionDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.ShippingOptionService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ShippingOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-options")
public class ShippingOptionController {

    @Autowired
    private final ShippingOptionService shippingOptionService;

    public ShippingOptionController(ShippingOptionService shippingOptionService) {
        this.shippingOptionService = shippingOptionService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ShippingOptionDTO>> getAllShippingOption(){
        final List<ShippingOptionDTO> optionsDTO= shippingOptionService.getAllShippingOptions().stream().map(
                ShippingOptionMapper::toDTO
        ).toList();
        return ResponseEntity.status(HttpStatus.OK).body(optionsDTO);
    }

}
