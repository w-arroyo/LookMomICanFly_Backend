package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Requests.AskRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AskService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.BidService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private final AuthService authService;
    private final BidService bidService;
    private final AskService askService;
    private final PostMapper postMapper;

    public PostController(AuthService authService, BidService bidService, AskService askService, PostMapper postMapper) {
        this.authService = authService;
        this.bidService = bidService;
        this.askService = askService;
        this.postMapper = postMapper;
    }

    @PostMapping("/asks/save")
    public ResponseEntity<Map<String,Object>> saveAsk(@RequestBody AskRequest askRequest){
        GlobalValidator.checkIfRequestBodyIsEmpty(askRequest);
        PostValidator.checkIfPostFieldsAreEmpty(askRequest, askRequest.getSellingFeeId(), "selling fee");
        authService.checkFraudulentRequest(askRequest.getUserId());
        ProductValidator.checkIfSizeExists(askRequest.getSize());
        final Ask ask= postMapper.toAsk(askRequest);
        ProductValidator.checkIfSizeBelongsToACategory(ask.getSize(),ask.getProduct().getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",askService.saveAsk(ask)));
    }

    @PostMapping("/bids/save")
    public ResponseEntity<Map<String,Object>> saveBid(@RequestBody BidRequest bidRequest){
        GlobalValidator.checkIfRequestBodyIsEmpty(bidRequest);
        PostValidator.checkIfPostFieldsAreEmpty(bidRequest, bidRequest.getShippingOptionId(), "shipping option");
        authService.checkFraudulentRequest(bidRequest.getUserId());
        ProductValidator.checkIfSizeExists(bidRequest.getSize());
        final Bid bid= postMapper.toBid(bidRequest);
        ProductValidator.checkIfSizeBelongsToACategory(bid.getSize(),bid.getProduct().getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",bidService.saveBid(bid)));
    }

}
