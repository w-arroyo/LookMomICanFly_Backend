package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.BidRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.BidService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PostService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
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
    private final PostMapper postMapper;
    private final TransactionMapper transactionMapper;
    private final BidService bidService;

    public BidController(AuthService authService, PostService postService, PostMapper postMapper, TransactionMapper transactionMapper, BidService bidService) {
        this.authService = authService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.transactionMapper = transactionMapper;
        this.bidService = bidService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveBid(@RequestBody BidRequestDTO bidRequest) throws Exception {
        Map<String,Object> response;
        final Object bidOrOrder;
        GlobalValidator.checkIfRequestBodyIsEmpty(bidRequest);
        PostValidator.checkIfPostFieldsAreEmpty(bidRequest);
        GlobalValidator.checkIfANumberIsGreaterThan(bidRequest.getAmount(), 1);
        authService.checkFraudulentRequest(bidRequest.getUserId());
        bidOrOrder=postService.saveBid(
                postMapper.toBid(bidRequest)
        );
        if(bidOrOrder instanceof Bid){
            response=Map.of("bid",postMapper.toBidDTO((Bid) bidOrOrder));
        }
        else{
            response=Map.of("order", transactionMapper.orderToTransactionSummaryDTO((Order) bidOrOrder));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/highest-bid/")
    public ResponseEntity<Integer> getHighestBidAmount(@RequestParam String productId, @RequestParam String size){
        GlobalValidator.checkIfTwoFieldsAreEmpty(productId,size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bidService.getHighestBidAmount(productId, ProductValidator.checkIfASizeExists(size)));
    }

}
