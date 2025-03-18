package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskService {

    @Autowired
    private final AskRepository askRepository;

    public AskService(AskRepository askRepository) {
        this.askRepository = askRepository;
    }

    @Transactional
    public Object saveAsk(Ask ask){
        return askRepository.save(ask).getId();
    }

    public Ask getLowestAsk(String productId, Size size){
        return askRepository.getLowestAskForASizeOfAProduct(productId, size).orElse(null);
    }

}
