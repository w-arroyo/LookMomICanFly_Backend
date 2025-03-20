package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SkateboardDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Skateboard;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SkateboardRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkateboardService {

    @Autowired
    private final SkateboardRepository skateboardRepository;
    private final ProductService productService;

    public SkateboardService(SkateboardRepository skateboardRepository, ProductService productService) {
        this.skateboardRepository = skateboardRepository;
        this.productService = productService;
    }

    public SkateboardDTO getSkateboardDTOById(String id){
        return ProductMapper.toSkateboardDTO(skateboardRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Skateboard id does not exist.")
        ));
    }

    @Transactional
    public Skateboard saveSkateboard(SkateboardDTO skateboardDTO){
        final Skateboard skateboard= ProductMapper.toSkateboard(skateboardDTO);
        ProductValidator.checkIfCategoryIsCorrect(skateboard.getCategory(), ProductCategory.SKATEBOARDS);
        productService.fillManufacturerAndColors(skateboard,skateboardDTO);
        skateboard.setId(skateboardRepository.save(skateboard).getId());
        productService.saveProductColors(skateboard);
        return skateboard;
    }

}
