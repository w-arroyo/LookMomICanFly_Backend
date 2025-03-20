package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SneakersDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sneakers;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SneakersRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SneakersService {

    @Autowired
    private final SneakersRepository sneakersRepository;
    private final ProductService productService;

    public SneakersService(SneakersRepository sneakersRepository, ProductService productService) {
        this.sneakersRepository = sneakersRepository;
        this.productService = productService;
    }

    @Transactional
    public Sneakers saveSneakers(SneakersDTO sneakersDTO){
        final Sneakers sneakers=ProductMapper.toSneakers(sneakersDTO);
        ProductValidator.checkIfCategoryIsCorrect(sneakers.getCategory(), ProductCategory.SNEAKERS);
        productService.fillManufacturerAndColors(sneakers,sneakersDTO);
        sneakers.setId(sneakersRepository.save(sneakers).getId());
        productService.saveProductColors(sneakers);
        return sneakers;
    }

    public SneakersDTO getSneakersDTOById(String id){
        try {
            return ProductMapper.toSneakersDTO(sneakersRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Sneakers id not found")));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
