package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.CollectibleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Collectible;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.CollectibleService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/collectibles")
public class CollectibleController {

    private final CollectibleService collectibleService;
    private final AuthService authService;

    public CollectibleController(CollectibleService collectibleService,AuthService authService) {
        this.collectibleService = collectibleService;
        this.authService = authService;
    }

    @GetMapping("/get/")
    public ResponseEntity<CollectibleDTO> getCollectibleDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(collectibleService.getCollectibleDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Collectible>> saveCollectible(@RequestBody CollectibleDTO collectibleDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(collectibleDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(collectibleDTO,collectibleDTO.getCollectionName(),"collection_name");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",collectibleService.saveCollectible(collectibleDTO)));
    }

}
