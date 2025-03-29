package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get/all-summary")
    public ResponseEntity<Map<String,List<ProductSummaryDTO>>> getAllProductsSummary(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("products",
                        productMapper.toSummaryList(productService.findAllProducts())));
    }

    @GetMapping("/get/all-summary-by-category/")
    public ResponseEntity<Map<String,List<ProductSummaryDTO>>> getCategorySummary(@RequestParam String category){
        GlobalValidator.checkIfAFieldIsEmpty(category);
        List<Product> productList = productService.findAllProductsByCategory(ProductValidator.checkIfProductCategoryExists(category));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("products",productMapper.toSummaryList(productList)));
    }

}
