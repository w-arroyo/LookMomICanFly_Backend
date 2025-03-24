package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    private final WebClient webClient;

    public ProductService(ProductRepository productRepository, WebClient webClient) {
        this.productRepository = productRepository;
        this.webClient = webClient;
    }

    public Product findProductById(String id){
        return productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Product id does not exist.")
        );
    }

    public ProductCategory getProductCategoryById(String id){
        try {
            return productRepository.getProductCategoryByProductId(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Class<?> returnProductClass(ProductCategory category){
        return switch (category){
            case SNEAKERS -> Sneakers.class;
            case CLOTHING -> Clothing.class;
            case ACCESSORIES -> Accessory.class;
            case SKATEBOARDS -> Skateboard.class;
            case COLLECTIBLES -> Collectible.class;
            case MUSIC -> Music.class;
        };
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> findAllProductsByCategory(ProductCategory category){
        return productRepository.findAllByCategory(category);
    }

    public <T>Mono<T> sendRequestToGetProductDTOByCategory(String id, String categoryAsString, Class<T> categoryClass){
        try{
            return webClient.get()
                    .uri("http://localhost:8080/api/products/"+categoryAsString.toLowerCase()+"/get/?id="+id)
                    .retrieve() // sends request
                    .bodyToMono(categoryClass) // casts response to mono<category class>
                    .onErrorMap(error -> new RuntimeException(error.getMessage()));
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
