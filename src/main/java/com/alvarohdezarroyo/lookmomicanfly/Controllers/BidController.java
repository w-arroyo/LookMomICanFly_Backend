package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.BidService;
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
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private final BidService bidService;
    private final PostMapper postMapper;
    private final AuthService authService;

    public BidController(BidService bidService, PostMapper postMapper, AuthService authService) {
        this.bidService = bidService;
        this.postMapper = postMapper;
        this.authService = authService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveBid(@RequestBody BidRequest bidRequest){
        GlobalValidator.checkIfRequestBodyIsEmpty(bidRequest);
        PostValidator.checkIfPostFieldsAreEmpty(bidRequest, bidRequest.getShippingOptionId(), "shipping option id");
        GlobalValidator.checkIfANumberIsGreaterThan(bidRequest.getAmount(), 1);
        authService.checkFraudulentRequest(bidRequest.getUserId());
        final Bid bid= postMapper.toBid(bidRequest);
        AddressValidator.checkIfAddressBelongsToAUser(bidRequest.getUserId(), bid.getAddress());
        ProductValidator.checkIfSizeBelongsToACategory(bid.getSize(),bid.getProduct().getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",bidService.saveBid(bid)));
    }





}
