package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.CollectibleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.CollectibleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/collectibles")
public class CollectibleController {

    private final CollectibleService collectibleService;

    public CollectibleController(CollectibleService collectibleService) {
        this.collectibleService = collectibleService;
    }

    @GetMapping("/get/")
    public ResponseEntity<CollectibleDTO> getCollectibleDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(collectibleService.getCollectibleDTOById(id));
    }

}
