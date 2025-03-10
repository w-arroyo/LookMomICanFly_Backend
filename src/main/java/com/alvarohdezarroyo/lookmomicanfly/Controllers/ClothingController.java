package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ClothingDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.ClothingService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/clothing")
public class ClothingController {

    @Autowired
    private final ClothingService clothingService;

    public ClothingController(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    @GetMapping("/get/")
    public ResponseEntity<ClothingDTO> getClothingDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK).body(clothingService.getClothingDTOById(id));
    }

}
