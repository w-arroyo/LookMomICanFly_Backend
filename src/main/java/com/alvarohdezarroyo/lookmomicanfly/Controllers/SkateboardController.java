package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SkateboardDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SkateboardService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/skateboards")
public class SkateboardController {

    private final SkateboardService skateboardService;
    private final AuthService authService;

    public SkateboardController(SkateboardService skateboardService,AuthService authService) {
        this.skateboardService = skateboardService;
        this.authService = authService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SkateboardDTO> getSkateboardDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(skateboardService.getSkateboardDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveSkateboard(@RequestBody SkateboardDTO skateboardDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(skateboardDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(skateboardDTO,skateboardDTO.getLength(),"length");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",skateboardService.saveSkateboard(skateboardDTO)));
    }

}
