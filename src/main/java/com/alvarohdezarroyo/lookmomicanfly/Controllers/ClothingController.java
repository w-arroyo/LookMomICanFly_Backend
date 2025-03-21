package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ClothingDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Clothing;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ClothingService;
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
    private final AuthService authService;

    public ClothingController(ClothingService clothingService, AuthService authService) {
        this.clothingService = clothingService;
        this.authService = authService;
    }

    @GetMapping("/get/")
    public ResponseEntity<ClothingDTO> getClothingDTOById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        return ResponseEntity.status(HttpStatus.OK).body(clothingService.getClothingDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Clothing>> saveClothingProduct(@RequestBody ClothingDTO clothingDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(clothingDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(clothingDTO, clothingDTO.getSeason(), "season");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",clothingService.saveClothing(clothingDTO)));
    }

}
