package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.CollectibleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Collectible;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.CollectibleRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectibleService {

    @Autowired
    private final CollectibleRepository collectibleRepository;
    private final ProductService productService;

    public CollectibleService(CollectibleRepository collectibleRepository, ProductService productService) {
        this.collectibleRepository = collectibleRepository;
        this.productService = productService;
    }

    public CollectibleDTO getCollectibleDTOById(String id){
        return ProductMapper.toCollectibleDTO(collectibleRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Collectible id does not exist.")
        ));
    }

    @Transactional
    public Collectible saveCollectible(CollectibleDTO collectibleDTO){
        final Collectible collectible= ProductMapper.toCollectible(collectibleDTO);
        ProductValidator.checkIfCategoryIsCorrect(collectible.getCategory(), ProductCategory.COLLECTIBLES);
        productService.fillManufacturerAndColors(collectible,collectibleDTO);
        collectible.setId(collectibleRepository.save(collectible).getId());
        productService.saveProductColors(collectible);
        return collectible;

    }

}
