package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SkateboardDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.SkateboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/skateboards")
public class SkateboardController {

    private final SkateboardService skateboardService;

    public SkateboardController(SkateboardService skateboardService) {
        this.skateboardService = skateboardService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SkateboardDTO> getSkateboardDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(skateboardService.getSkateboardDTOById(id));
    }

}
