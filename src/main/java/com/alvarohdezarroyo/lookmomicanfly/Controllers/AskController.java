package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.PostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AskService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
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
@RequestMapping("/api/asks")
public class AskController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;
    private final TransactionMapper transactionMapper;
    private final PostMapper postMapper;
    private final AskService askService;


    public AskController(AuthService authService, PostService postService, TransactionMapper transactionMapper, PostMapper postMapper, AskService askService) {
        this.authService = authService;
        this.postService = postService;
        this.transactionMapper = transactionMapper;
        this.postMapper = postMapper;
        this.askService = askService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveAsk(@RequestBody PostRequestDTO askRequest) throws Exception {
        Map<String,Object> response;
        final Object askOrSale;
        GlobalValidator.checkIfRequestBodyIsEmpty(askRequest);
        PostValidator.checkIfPostFieldsAreEmpty(askRequest);
        GlobalValidator.checkIfANumberIsGreaterThan(askRequest.getAmount(),1);
        authService.checkFraudulentRequest(askRequest.getUserId());
        askOrSale=postService.saveAsk(
                postMapper.toAsk(askRequest)
        );
        if(askOrSale instanceof Ask){
            response=Map.of("ask",postMapper.toAskDTO((Ask) askOrSale));
        }
        else{
            response=Map.of("sale",transactionMapper.saleToTransactionSummaryDTO((Sale) askOrSale));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/lowest-ask/")
    public ResponseEntity<Integer> getLowestAskAmount(@RequestParam String productId, @RequestParam String size){
        GlobalValidator.checkIfTwoFieldsAreEmpty(productId,size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(askService.getLowestAskAmount(productId, ProductValidator.checkIfASizeExists(size)));
    }

}
