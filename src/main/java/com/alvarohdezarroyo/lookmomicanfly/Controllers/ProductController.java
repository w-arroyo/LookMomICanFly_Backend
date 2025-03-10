package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get/all/")
    public ResponseEntity<Map<String,Object>> getAllProductsSummary(){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("products",productService.getAllProductsSummary()));
    }

    @GetMapping("/get/")
    public Mono<ResponseEntity<Map<String,Object>>> findProductById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        final ProductCategory productCategory=productService.getProductCategoryById(id);
        return productService.sendRequestToGetProductDTOByCategory(id, productCategory.name(), productService.returnProductClass(productCategory))
                .flatMap(productDto -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.OK).body(Map.of(productCategory.name().toLowerCase(), productDto)));
                });
    }

}
