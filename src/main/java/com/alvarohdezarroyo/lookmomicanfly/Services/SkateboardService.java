package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SkateboardDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Skateboard;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SkateboardRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkateboardService {

    @Autowired
    private final SkateboardRepository skateboardRepository;

    public SkateboardService(SkateboardRepository skateboardRepository) {
        this.skateboardRepository = skateboardRepository;
    }

    public SkateboardDTO getSkateboardDTOById(String id){
        return ProductMapper.toSkateboardDTO(skateboardRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Skateboard id does not exist.")
        ));
    }

    @Transactional
    public Skateboard saveSkateboard(Skateboard skateboard){
        return skateboardRepository.save(skateboard);
    }

}
