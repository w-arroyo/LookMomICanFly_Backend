package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sneakers;
import org.mapstruct.Mapper;

@Mapper
public class ProductMapper {

    public static ProductDTO toDTO(Product product){
        ProductDTO productDTO=new ProductDTO();
        fillProductDTOFields(productDTO,product);
        return productDTO;
    }

    public static void fillProductDTOFields(ProductDTO productDTO, Product product){
        productDTO.setId(product.getId());
        productDTO.setName(productDTO.getName());
        productDTO.setActive(product.getActive());
        productDTO.setManufacturer(product.getManufacturer().getName());
        productDTO.setCategory(product.getProductCategory().name());
        productDTO.setSubcategory(product.getProductSubcategory().name());
        productDTO.setReleaseYear(product.getReleaseYear());
        productDTO.setColors(product.getColors());
    }

    public static Product toProduct(ProductDTO productDTO){
        Product product=new Product();
        product.setName(productDTO.getName());
        // left to complete later

        return product;
    }

    public static ProductSummaryDTO toSummary(Product product){
        ProductSummaryDTO productSummaryDTO=new ProductSummaryDTO();
        productSummaryDTO.setId(product.getId());
        productSummaryDTO.setName(product.getName());
        productSummaryDTO.setYear(product.getReleaseYear());
        productSummaryDTO.setManufacturer(product.getManufacturer().getName());
        return productSummaryDTO;
    }

    public static SneakersDTO toSneakersDTO(Sneakers sneakers){
        SneakersDTO sneakersDTO=new SneakersDTO();
        fillProductDTOFields(sneakersDTO,sneakers);
        sneakersDTO.setSku(sneakers.getSku());
        return sneakersDTO;
    }

}
