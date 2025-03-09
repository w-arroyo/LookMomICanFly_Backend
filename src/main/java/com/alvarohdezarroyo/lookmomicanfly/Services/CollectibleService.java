package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Repositories.CollectibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectibleService {

    @Autowired
    private final CollectibleRepository collectibleRepository;

    public CollectibleService(CollectibleRepository collectibleRepository) {
        this.collectibleRepository = collectibleRepository;
    }
}
