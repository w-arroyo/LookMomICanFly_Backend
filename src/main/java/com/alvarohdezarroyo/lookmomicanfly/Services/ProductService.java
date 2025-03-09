package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.NoDataFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ProductRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
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

    public ProductCategory getProductCategoryById(String id){
        try {
            return productRepository.getProductCategoryByProductId(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Class<?> returnProductClass(ProductCategory category){
        return switch (category){
            case SNEAKERS -> SneakersDTO.class;
            case CLOTHING -> Clothing.class;
            case ACCESSORIES -> Accesory.class;
            case SKATEBOARDS -> Skateboard.class;
            case COLLECTIBLES -> Collectible.class;
            case MUSIC -> Music.class;
        };
    }


    public ProductSummaryDTO[] getAllProductsSummary(){
        try{
            List<Product> productList=productRepository.findAll();
            if(productList.isEmpty())
                throw new NoDataFoundException("No products in the database.");
            ProductSummaryDTO[] list=new ProductSummaryDTO[productList.size()];
            for (int product = 0; product < productList.size(); product++) {
                list[product]= ProductMapper.toSummary(productList.get(product));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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
