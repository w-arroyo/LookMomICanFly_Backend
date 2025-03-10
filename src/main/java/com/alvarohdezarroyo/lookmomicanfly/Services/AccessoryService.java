package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AccessoryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Accesory;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AccesoryRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessoryService {

    @Autowired
    private final AccesoryRepository accesoryRepository;

    public AccessoryService(AccesoryRepository accesoryRepository) {
        this.accesoryRepository = accesoryRepository;
    }

    public AccessoryDTO getAccessoryDTOById(String id){
        return ProductMapper.toAccessoryDTO(accesoryRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Accessory id does not exist.")
        ));
    }

    public Accesory saveAccessory(Accesory accesory){
        return accesoryRepository.save(accesory);
    }
}
