package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Requests.AskRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AskService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.AddressValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/asks")
public class AskController {

    @Autowired
    private final AskService askService;
    private final PostMapper postMapper;
    private final AuthService authService;

    public AskController(AskService askService, PostMapper postMapper, AuthService authService) {
        this.askService = askService;
        this.postMapper = postMapper;
        this.authService = authService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveAsk(@RequestBody AskRequest askRequest){
        GlobalValidator.checkIfRequestBodyIsEmpty(askRequest);
        PostValidator.checkIfPostFieldsAreEmpty(askRequest, askRequest.getSellingFeeId(), "selling fee");
        GlobalValidator.checkIfANumberIsGreaterThan(askRequest.getAmount(),1);
        authService.checkFraudulentRequest(askRequest.getUserId());
        final Ask ask= postMapper.toAsk(askRequest);
        ProductValidator.checkIfSizeBelongsToACategory(ask.getSize(),ask.getProduct().getCategory());
        AddressValidator.checkIfAddressBelongsToAUser(askRequest.getUserId(), ask.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",askService.saveAsk(ask)));
    }

}
