package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

}
