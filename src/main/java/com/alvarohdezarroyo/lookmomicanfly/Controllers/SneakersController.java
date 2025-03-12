package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sneakers;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SneakersService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/sneakers")
public class SneakersController {

    @Autowired
    private final SneakersService sneakersService;
    private final ProductService productService;

    public SneakersController(SneakersService sneakersService, ProductService productService) {
        this.sneakersService = sneakersService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SneakersDTO> getSneakersById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(sneakersService.getSneakersDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveSneakers(@RequestBody SneakersDTO sneakersDTO){
        //remember to validate user is ADMIN to allow this request
        GlobalValidator.checkIfRequestBodyIsEmpty(sneakersDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(sneakersDTO,sneakersDTO.getSku(),"sku");
        final Sneakers sneakers=ProductMapper.toSneakers(sneakersDTO);
        ProductValidator.checkIfCategoryIsCorrect(sneakers.getCategory(), ProductCategory.SNEAKERS);
        productService.fillManufacturerAndColors(sneakers,sneakersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",sneakersService.saveSneakers(sneakers)));
    }

}
