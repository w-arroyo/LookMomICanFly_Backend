package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Repositories.SkateboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkateboardService {

    @Autowired
    private final SkateboardRepository skateboardRepository;

    public SkateboardService(SkateboardRepository skateboardRepository) {
        this.skateboardRepository = skateboardRepository;
    }
}
