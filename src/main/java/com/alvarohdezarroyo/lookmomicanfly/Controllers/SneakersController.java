package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.SneakersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/sneakers")
public class SneakersController {

    @Autowired
    private final SneakersService sneakersService;

    public SneakersController(SneakersService sneakersService) {
        this.sneakersService = sneakersService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SneakersDTO> getSneakersById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(sneakersService.getSneakersDTOById(id));
    }

}
