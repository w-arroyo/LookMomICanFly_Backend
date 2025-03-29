package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.ProductAlreadyLikedException;
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
    private final AuthService authService;

    public ProductController(ProductService productService, ProductMapper productMapper, AuthService authService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.authService = authService;
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

    @GetMapping("/favorites/list/")
    public ResponseEntity<Map<String,List<ProductSummaryDTO>>> getUserLikedProducts(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Product> list= productService.findUserLikedProducts(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("products",productMapper.toSummaryList(list)));
    }

    @GetMapping("/favorites/check/")
    public ResponseEntity<Boolean> checkIfUserLikedAProduct(@RequestParam String userId, @RequestParam String productId){
        checkLikingProducts(userId,productId);
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.checkIfUserLikesAProduct(userId,productId)
        );
    }

    @PostMapping("/favorites/like/")
    public ResponseEntity<String> likeProduct(@RequestParam String userId, @RequestParam String productId){
        checkLikingProducts(userId,productId);
        if(!productService.checkIfUserLikesAProduct(userId,productId))
            throw new ProductAlreadyLikedException("You already like this product.");
        productService.likeProduct(userId,productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PutMapping("/favorites/unlike/")
    public ResponseEntity<String> unlikeProduct(@RequestParam String userId, @RequestParam String productId){
        checkLikingProducts(userId,productId);
        if(productService.checkIfUserLikesAProduct(userId,productId))
            throw new ProductAlreadyLikedException("You already do not like this product.");
        productService.unlikeProduct(userId,productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    private void checkLikingProducts(String userId, String productId){
        GlobalValidator.checkIfTwoFieldsAreEmpty(userId,productId);
        authService.checkFraudulentRequest(userId);
    }

}
