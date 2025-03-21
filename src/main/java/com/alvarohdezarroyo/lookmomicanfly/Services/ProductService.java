package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ManufacturerRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ProductRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final WebClient webClient;
    private final ColorService colorService;

    public ProductService(ProductRepository productRepository, ManufacturerRepository manufacturerRepository, WebClient webClient, ColorService colorService) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.webClient = webClient;
        this.colorService = colorService;
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
            case SNEAKERS -> SneakersDTO.class;
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


    public ProductSummaryDTO[] moveProductListToSummaryList(List<Product> productList){
        try{
            if(productList.isEmpty())
                return new ProductSummaryDTO[0];
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

    private Manufacturer getManufacturerByName(String name){
        return manufacturerRepository.findByName(name).orElseThrow(
                ()->new EntityNotFoundException("Manufacturer name does not exist.")
        );
    }

    private List<Color> fillColorListFromDTO(String [] colorDTOs){
        List<Color> colors=new ArrayList<>();
        for (String colorDTO: colorDTOs){
            colors.add(colorService.findColorByName(colorDTO));
        }
        return colors;
    }

    @Transactional
    public void saveProductColors(Product product){
        colorService.saveProductColors(product);
    }

    public void fillManufacturerAndColors(Product product, ProductDTO productDTO){
        product.setManufacturer(getManufacturerByName(productDTO.getManufacturer()));
        product.setColors(fillColorListFromDTO(productDTO.getColors()));
    }

}
