package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SneakersService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
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
    private final AuthService authService;

    public SneakersController(SneakersService sneakersService,AuthService authService) {
        this.sneakersService = sneakersService;
        this.authService = authService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SneakersDTO> getSneakersById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(sneakersService.getSneakersDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveSneakers(@RequestBody SneakersDTO sneakersDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(sneakersDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(sneakersDTO,sneakersDTO.getSku(),"sku");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",sneakersService.saveSneakers(sneakersDTO)));
    }

}
