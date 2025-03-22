package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.ClothingDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Clothing;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.ClothingRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClothingService {

    @Autowired
    private final ClothingRepository clothingRepository;
    private final ProductService productService;

    public ClothingService(ClothingRepository clothingRepository, ProductService productService) {
        this.clothingRepository = clothingRepository;
        this.productService = productService;
    }

    public ClothingDTO getClothingDTOById(String id){
        return ProductMapper.toClothingDTO(clothingRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Clothing id does not exist.")
        ));
    }

    @Transactional
    public Clothing saveClothing(ClothingDTO clothingDTO){
        final Clothing clothing= ProductMapper.toClothing(clothingDTO);
        ProductValidator.checkIfCategoryIsCorrect(clothing.getCategory(), ProductCategory.CLOTHING);
        productService.fillManufacturerAndColors(clothing,clothingDTO);
        clothing.setId(clothingRepository.save(clothing).getId());
        productService.saveProductColors(clothing);
        return clothing;
    }

}
