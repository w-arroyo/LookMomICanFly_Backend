package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ProductDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductSubcategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public static void checkIfCategoryIsCorrect(ProductCategory categoryToCheck, ProductCategory category){
        if(!category.equals(categoryToCheck))
            throw new IllegalArgumentException("Wrong endpoint.");
    }

    public static Size checkIfASizeExists(String sizeString){
        for(Size size: Size.values()){
            if(size.getValue().equalsIgnoreCase(sizeString))
                return size;
        }
        throw new EntityNotFoundException("Size does not exist.");
    }

    public static void checkIfSizeBelongsToACategory(Size size, ProductCategory category){
        if(!size.getCategories().contains(category))
            throw new IllegalArgumentException("Size does not belong to that category");
    }

    public static void checkSubcategoriesByCategory(String category){
        if(ProductSubcategory.getSubcategoriesByCategory(category).isEmpty())
            throw new EntityNotFoundException("Product category does not have any subcategories.");
    }

    public static void checkIfSubcategoryBelongsToACategory(ProductCategory productCategory,ProductSubcategory productSubcategory){
        if(!ProductSubcategory.checkIfSubcategoryBelongsToACategory(productCategory,productSubcategory))
            throw new IllegalArgumentException("Subcategory does not belong to that category.");
    }

    public static void checkIfProductFieldsAreEmpty(ProductDTO productDTO, String specialField, String specialFieldName){
        final List<String> emptyFields=new ArrayList<>();
        if(productDTO.getName()==null || productDTO.getName().trim().isEmpty())
            emptyFields.add("name");
        if(productDTO.getCategory()==null || productDTO.getCategory().trim().isEmpty())
            emptyFields.add("category");
        if(productDTO.getSubcategory()==null || productDTO.getSubcategory().trim().isEmpty())
            emptyFields.add("category");
        try {
            GlobalValidator.checkIfANumberFieldIsValid(productDTO.getReleaseYear());
        }
        catch (IllegalArgumentException e){
            emptyFields.add("release_year");
        }
        if(productDTO.getManufacturer()==null || productDTO.getManufacturer().trim().isEmpty())
            emptyFields.add("manufacturer");
        if(productDTO.getColors()==null || productDTO.getColors().length==0)
            emptyFields.add("colors");
        if(specialField==null || specialField.trim().isEmpty())
            emptyFields.add(specialFieldName);
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

}
