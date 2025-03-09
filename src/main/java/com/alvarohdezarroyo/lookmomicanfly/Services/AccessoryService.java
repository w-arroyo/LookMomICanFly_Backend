package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Repositories.AccesoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessoryService {

    @Autowired
    private final AccesoryRepository accesoryRepository;

    public AccessoryService(AccesoryRepository accesoryRepository) {
        this.accesoryRepository = accesoryRepository;
    }
}
