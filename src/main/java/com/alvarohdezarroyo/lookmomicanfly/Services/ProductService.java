package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.NoDataFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ProductRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
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

}
