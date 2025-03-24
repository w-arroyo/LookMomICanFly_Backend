package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/get/")
    public Mono<ResponseEntity<Map<String,Object>>> findProductById(@RequestParam String id){
        GlobalValidator.checkIfAFieldIsEmpty(id);
        final ProductCategory productCategory=productService.getProductCategoryById(id);
        return productService.sendRequestToGetProductDTOByCategory(id, productCategory.name(), productService.returnProductClass(productCategory))
                .flatMap(productDto -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.OK)
                            .body(Map.of(productCategory.name().toLowerCase(), productDto)));
                });
    }

    @GetMapping("/get/all-summary")
    public ResponseEntity<List<ProductSummaryDTO>> getAllProductsSummary(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productMapper.toSummaryList(productService.findAllProducts()));
    }

    @GetMapping("/get/all-summary-by-category/")
    public ResponseEntity<Map<String,List<ProductSummaryDTO>>> getCategorySummary(@RequestParam String category){
        GlobalValidator.checkIfAFieldIsEmpty(category);
        List<Product> productList = productService.findAllProductsByCategory(ProductValidator.checkIfProductCategoryExists(category));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("products",productMapper.toSummaryList(productList)));
    }

}
