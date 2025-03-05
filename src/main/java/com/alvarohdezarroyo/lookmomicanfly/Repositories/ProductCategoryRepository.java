package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,String> {
}
