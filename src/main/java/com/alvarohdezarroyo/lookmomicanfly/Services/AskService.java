package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
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
    public Ask saveAsk(Ask ask){
        return askRepository.save(ask);
    }

    public Ask findAskById(String id){
        return askRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Ask ID does not exist.")
        );
    }

    public Ask getLowestAsk(String productId, Size size){
        return askRepository.getLowestAskForASizeOfAProduct(productId, size).orElse(null);
    }

    public Integer getLowestAskAmount(String productId, Size size){
        return askRepository.getLowestAskAmount(productId,size.getValue());
    }

}
