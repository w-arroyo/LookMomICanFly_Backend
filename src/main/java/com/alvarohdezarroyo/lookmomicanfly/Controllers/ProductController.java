package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
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

    @GetMapping("/get/")
    public Mono<ResponseEntity<Map<String,Object>>> findProductById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        final ProductCategory productCategory=productService.getProductCategoryById(id);
        return productService.sendRequestToGetProductDTOByCategory(id, productCategory.name(), productService.returnProductClass(productCategory))
                .flatMap(productDto -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.OK).body(Map.of(productCategory.name().toLowerCase(), productDto)));
                });
    }

    @GetMapping("/get/all-summary")
    public ResponseEntity<ProductSummaryDTO[]> getAllProductsSummary(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.moveProductListToSummaryList(productService.findAllProducts()));
    }

    @GetMapping("/get/all-summary-by-category/")
    public ResponseEntity<ProductSummaryDTO[]> getCategorySummary(@RequestParam String category){
        GlobalValidator.checkIfAFieldIsEmpty(category);
        return ResponseEntity.status(HttpStatus.OK).body(productService.moveProductListToSummaryList(productService.findAllProductsByCategory(ProductValidator.checkIfProductCategoryExists(category))));
    }

}
