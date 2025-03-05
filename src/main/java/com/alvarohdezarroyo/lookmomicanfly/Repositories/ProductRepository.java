package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
