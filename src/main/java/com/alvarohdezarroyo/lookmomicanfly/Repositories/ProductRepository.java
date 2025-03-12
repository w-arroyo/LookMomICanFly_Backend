package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    
    List<Product> findAllByCategory(ProductCategory category);

    @Query(value = "SELECT category from products where id= :id", nativeQuery = true)
    ProductCategory getProductCategoryByProductId(@Param("id") String id);

}
