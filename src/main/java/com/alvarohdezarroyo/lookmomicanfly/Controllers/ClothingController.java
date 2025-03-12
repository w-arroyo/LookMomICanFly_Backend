package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ClothingDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Clothing;
import com.alvarohdezarroyo.lookmomicanfly.Services.ClothingService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/clothing")
public class ClothingController {

    @Autowired
    private final ClothingService clothingService;
    private final ProductService productService;

    public ClothingController(ClothingService clothingService, ProductService productService) {
        this.clothingService = clothingService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<ClothingDTO> getClothingDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK).body(clothingService.getClothingDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Clothing>> saveClothingProduct(@RequestBody ClothingDTO clothingDTO){
        //remember to validate user is ADMIN to allow this request
        GlobalValidator.checkIfRequestBodyIsEmpty(clothingDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(clothingDTO, clothingDTO.getSeason(), "season");
        final Clothing clothing= ProductMapper.toClothing(clothingDTO);
        ProductValidator.checkIfCategoryIsCorrect(clothing.getCategory(), ProductCategory.CLOTHING);
        productService.fillManufacturerAndColors(clothing,clothingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",clothingService.saveClothing(clothing)));
    }

}
