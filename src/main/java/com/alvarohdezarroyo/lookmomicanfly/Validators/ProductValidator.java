package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductSubcategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.SizeRun;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public static ProductCategory checkIfProductCategoryExists(String category){
        for(ProductCategory productCategory: ProductCategory.values()){
            if(productCategory.name().equalsIgnoreCase(category))
                return productCategory;
        }
        throw new EntityNotFoundException("Product category does not exist.");
    }

    public static ProductSubcategory checkIfProductSubcategoryExists(String subcategory){
        for(ProductSubcategory productSubcategory: ProductSubcategory.values()){
            if(productSubcategory.name().equalsIgnoreCase(subcategory))
                return productSubcategory;
        }
        throw new EntityNotFoundException("Product subcategory does not exist.");
    }

    public static SizeRun checkIfSizeExists(String check){
        for(SizeRun size: SizeRun.values()){
            if (size.getValue().equalsIgnoreCase(check))
                return size;
        }
        throw new EntityNotFoundException("Size does not exists.");
    }

    public static void checkIfSizeBelongsToACategory(SizeRun size, ProductCategory category){
        if(!size.getCategories().contains(category))
            throw new IllegalArgumentException("Size does not belong to that category");
    }

    public static void checkSubcategoriesByCategory(String category){
        if(ProductSubcategory.getSubcategoriesByCategory(category).isEmpty())
            throw new EntityNotFoundException("Product category does not have any subcategories.");
    }

}
