package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AccessoryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Accesory;
import com.alvarohdezarroyo.lookmomicanfly.Services.AccessoryService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/accessories")
public class AccessoryController {

    private final AccessoryService accessoryService;
    private final ProductService productService;

    public AccessoryController(AccessoryService accessoryService, ProductService productService) {
        this.accessoryService = accessoryService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<AccessoryDTO> getAccessoryDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(accessoryService.getAccessoryDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveAccessory(@RequestBody AccessoryDTO accessoryDTO){
        //remember to validate user is ADMIN to allow this request
        GlobalValidator.checkIfRequestBodyIsEmpty(accessoryDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(accessoryDTO,accessoryDTO.getMaterial(),"material");
        final Accesory accessory= ProductMapper.toAccessory(accessoryDTO);
        productService.fillManufacturerAndColors(accessory,accessoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",accessoryService.saveAccessory(accessory)));
    }

}
