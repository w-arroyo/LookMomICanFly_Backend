package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Repositories.AskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskService {

    @Autowired
    private final AskRepository askRepository;

    public AskService(AskRepository askRepository) {
        this.askRepository = askRepository;
    }

}
