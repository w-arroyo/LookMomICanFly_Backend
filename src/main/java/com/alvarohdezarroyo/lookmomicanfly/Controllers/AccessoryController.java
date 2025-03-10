package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AccessoryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AccessoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/accessories")
public class AccessoryController {

    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @GetMapping("/get/")
    public ResponseEntity<AccessoryDTO> getAccessoryDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(accessoryService.getAccessoryDTOById(id));
    }
}
