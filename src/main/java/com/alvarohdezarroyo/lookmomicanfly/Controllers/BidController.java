package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.BidDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostContainerDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Models.Product;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.BidRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.UpdatePostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    private final ProductService productService;

    public BidController(AuthService authService, PostService postService, PostMapper postMapper, TransactionMapper transactionMapper, BidService bidService, ProductService productService) {
        this.authService = authService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.transactionMapper = transactionMapper;
        this.bidService = bidService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<Map<String, BidDTO>> getBidById(@RequestParam String bidId, @RequestParam String userId) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(bidId,userId);
        authService.checkFraudulentRequest(userId);
        final Bid foundBid=bidService.findBidById(bidId);
        GlobalValidator.checkIfDataBelongToRequestingUser(userId,foundBid.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("bid",
                        postMapper.toBidDTO(foundBid)));
    }

    @GetMapping("/get-all/")
    public ResponseEntity<Map<String,PostSummaryDTO[]>> getAllUserBids(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Bid> bids=bidService.getAllUserBids(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("bids",
                        postMapper.bidListToSummaryDTO(bids)));
    }

    @GetMapping("/get/product/")
    public ResponseEntity<Map<String,List<PostContainerDTO>>> getAllProductBids(@RequestParam String productId){
        GlobalValidator.checkIfAFieldIsEmpty(productId);
        final Product product=productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("bids",
                        postMapper.bidListToContainer(
                                findAllProductBids(product)
                        )));
    }

    private Map<String,Bid> findAllProductBids(Product product){
        final Map<String,Bid> bids=new HashMap<>();
        Size.getSizesByCategory(product.getCategory()).forEach(
                size -> {
                    bids.put(size.getValue(),bidService.getHighestBid(product.getId(),size));
                }
        );
        return bids;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveBid(@RequestBody BidRequestDTO bidRequest) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(bidRequest);
        PostValidator.checkIfPostFieldsAreEmpty(bidRequest);
        GlobalValidator.checkIfANumberIsGreaterThan(bidRequest.getAmount(), 1);
        authService.checkFraudulentRequest(bidRequest.getUserId());
        final Object bidOrOrder=postService.saveBid(
                postMapper.toBid(bidRequest)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(returnOrderOrBid(bidOrOrder));
    }

    @GetMapping("/highest-bid/")
    public ResponseEntity<Integer> getHighestBidAmount(@RequestParam String productId, @RequestParam String size){
        GlobalValidator.checkIfTwoFieldsAreEmpty(productId,size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bidService.getHighestBidAmount(productId, ProductValidator.checkIfASizeExists(size)));
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String,Object>> updateBidAmount(@RequestBody UpdatePostRequestDTO bidToUpdate) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(bidToUpdate);
        PostValidator.checkPostToUpdateFields(bidToUpdate);
        authService.checkFraudulentRequest(bidToUpdate.getUserId());
        final Object updatedBidOrOrder=postService.updateBid(bidToUpdate.getPostId(),bidToUpdate.getAmount(), bidToUpdate.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(returnOrderOrBid(updatedBidOrOrder));
    }

    private Map<String,Object> returnOrderOrBid(Object bidOrOrder) throws Exception {
        Map<String,Object> response;
        if(bidOrOrder instanceof Bid){
            response=Map.of("bid",postMapper.toBidDTO((Bid) bidOrOrder));
        }
        else{
            response=Map.of("order", transactionMapper.orderToTransactionSummaryDTO((Order) bidOrOrder));
        }
        return response;
    }

}
