package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PostService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;

    public BidController(AuthService authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveBid(@RequestBody BidRequest bidRequest) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(bidRequest);
        PostValidator.checkIfPostFieldsAreEmpty(bidRequest);
        GlobalValidator.checkIfANumberIsGreaterThan(bidRequest.getAmount(), 1);
        authService.checkFraudulentRequest(bidRequest.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.saveBidFromRequest(bidRequest));
    }





}
