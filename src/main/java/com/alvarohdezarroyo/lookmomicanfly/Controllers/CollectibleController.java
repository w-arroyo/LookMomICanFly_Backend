package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.CollectibleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Collectible;
import com.alvarohdezarroyo.lookmomicanfly.Services.CollectibleService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
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
    private final ProductService productService;

    public CollectibleController(CollectibleService collectibleService, ProductService productService) {
        this.collectibleService = collectibleService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<CollectibleDTO> getCollectibleDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(collectibleService.getCollectibleDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveCollectible(@RequestBody CollectibleDTO collectibleDTO){
        //remember to validate user is ADMIN to allow this request
        GlobalValidator.checkIfRequestBodyIsEmpty(collectibleDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(collectibleDTO,collectibleDTO.getCollectionName(),"collection_name");
        final Collectible collectible= ProductMapper.toCollectible(collectibleDTO);
        productService.fillManufacturerAndColors(collectible,collectibleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",collectibleService.saveCollectible(collectible)));
    }

}
