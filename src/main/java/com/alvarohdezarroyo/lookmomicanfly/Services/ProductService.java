package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.NoDataFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ColorRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ManufacturerRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ProductRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
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
    private final ColorRepository colorRepository;
    private final WebClient webClient;

    public ProductService(ProductRepository productRepository, ManufacturerRepository manufacturerRepository, ColorRepository colorRepository, WebClient webClient) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.colorRepository = colorRepository;
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
        //return productRepository.findAllByCategory(category);
        return productRepository.findAllByCategory(category);
    }


    public ProductSummaryDTO[] moveProductListToSummaryList(List<Product> productList){
        try{
            if(productList.isEmpty())
                throw new NoDataFoundException("No products saved in the database.");
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
            colors.add(colorRepository.findByName(colorDTO).orElseThrow(
                    ()-> new EntityNotFoundException("Color not found")
            ));
        }
        return colors;
    }

    public void fillManufacturerAndColors(Product product, ProductDTO productDTO){
        product.setManufacturer(getManufacturerByName(productDTO.getManufacturer()));
        product.setColors(fillColorListFromDTO(productDTO.getColors()));
    }

}
