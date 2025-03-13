package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SkateboardDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Skateboard;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SkateboardService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/skateboards")
public class SkateboardController {

    private final SkateboardService skateboardService;
    private final ProductService productService;

    public SkateboardController(SkateboardService skateboardService, ProductService productService) {
        this.skateboardService = skateboardService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<SkateboardDTO> getSkateboardDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(skateboardService.getSkateboardDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveSkateboard(@RequestBody SkateboardDTO skateboardDTO){
        //remember to validate user is ADMIN to allow this request
        GlobalValidator.checkIfRequestBodyIsEmpty(skateboardDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(skateboardDTO,skateboardDTO.getLength(),"length");
        final Skateboard skateboard= ProductMapper.toSkateboard(skateboardDTO);
        ProductValidator.checkIfCategoryIsCorrect(skateboard.getCategory(), ProductCategory.SKATEBOARDS);
        productService.fillManufacturerAndColors(skateboard,skateboardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",skateboardService.saveSkateboard(skateboard)));
    }

}
