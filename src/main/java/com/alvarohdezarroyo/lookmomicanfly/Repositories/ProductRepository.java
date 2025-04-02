package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    
    List<Product> findAllByCategory(ProductCategory category);

    @Query(value = "SELECT category from products where id= :id", nativeQuery = true)
    ProductCategory getProductCategoryByProductId(@Param("id") String id);

    @Query(value = "SELECT COUNT(*) FROM users_favorite_products WHERE user_id= :userId AND product_id= :productId", nativeQuery = true)
    int checkIfAUserLikesAProduct(@Param("userId") String userId, @Param("productId") String productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_favorite_products WHERE user_id= :userId AND product_id= :productId", nativeQuery = true)
    void unlikeProduct(@Param("userId") String userId, @Param("productId") String productId);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO users_favorite_products VALUES ( :productId , :userId )", nativeQuery = true)
    void likeProduct(@Param("userId") String userId, @Param("productId") String productId);

    @Query(value = "SELECT p FROM Product p JOIN p.users u WHERE u.id = :id")
    List<Product> getUserLikedProducts(@Param("id") String id);

    @Query(value = "SELECT product FROM Product product WHERE name ILIKE %:name%")
    List<Product> findProductsByName(@Param("name") String name);

}
