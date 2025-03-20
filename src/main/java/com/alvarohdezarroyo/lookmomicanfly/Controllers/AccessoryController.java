package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AccessoryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Accessory;
import com.alvarohdezarroyo.lookmomicanfly.Services.AccessoryService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
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
    private final AuthService authService;

    public AccessoryController(AccessoryService accessoryService, AuthService authService) {
        this.accessoryService = accessoryService;
        this.authService = authService;
    }

    @GetMapping("/get/")
    public ResponseEntity<AccessoryDTO> getAccessoryDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(accessoryService.getAccessoryDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Accessory>> saveAccessory(@RequestBody AccessoryDTO accessoryDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(accessoryDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(accessoryDTO,accessoryDTO.getMaterial(),"material");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",accessoryService.saveAccessory(accessoryDTO)));
    }

}
