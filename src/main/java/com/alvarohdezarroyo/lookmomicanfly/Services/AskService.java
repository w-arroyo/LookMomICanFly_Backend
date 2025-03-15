package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
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

    public Object saveAsk(Ask ask){
        System.out.println(ask.getSize().name()+"/"+ask.getSize());
        final Ask askTwo=askRepository.save(ask);
        System.out.println(askTwo.getSize()+"/"+askTwo.getProduct().getName()+"/"+askTwo.getUser().getEmail()+"/"+askTwo.getShippingFee()+"/"+askTwo.getAmount()+"/");
        return "";
    }

}
