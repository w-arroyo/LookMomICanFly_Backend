package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AccessoryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Accessory;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AccesoryRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessoryService {

    @Autowired
    private final AccesoryRepository accesoryRepository;
    private final ProductService productService;

    public AccessoryService(AccesoryRepository accesoryRepository, ProductService productService) {
        this.accesoryRepository = accesoryRepository;
        this.productService = productService;
    }

    public AccessoryDTO getAccessoryDTOById(String id){
        return ProductMapper.toAccessoryDTO(accesoryRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Accessory id does not exist.")
        ));
    }

    @Transactional
    public Accessory saveAccessory(AccessoryDTO accessoryDTO){
        final Accessory accessory= ProductMapper.toAccessory(accessoryDTO);
        ProductValidator.checkIfCategoryIsCorrect(accessory.getCategory(), ProductCategory.ACCESSORIES);
        productService.fillManufacturerAndColors(accessory,accessoryDTO);
        accessory.setId(accesoryRepository.save(accessory).getId());
        productService.saveProductColors(accessory);
        return accessory;
    }

}
