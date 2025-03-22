package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SaleDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SaleService;
import com.alvarohdezarroyo.lookmomicanfly.Services.TransactionStatusService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private final TransactionStatusService transactionStatusService;
    private final AuthService authService;
    private final SaleService saleService;

    public SaleController(TransactionStatusService transactionStatusService, AuthService authService, SaleService saleService) {
        this.transactionStatusService = transactionStatusService;
        this.authService = authService;
        this.saleService = saleService;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTransactions(){
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusService.changeSaleStatus());
    }

    @PostMapping("/new-sale-tracking/")
    public ResponseEntity<String> generateNewSaleTrackingNumber(@RequestParam String saleId, @RequestParam String userId){
        basicValidations(saleId,userId);
        saleService.generateNewSaleTrackingNumber(saleId);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/get-sale/")
    public ResponseEntity<Map<String,SaleDTO>> getSaleDTO(@RequestParam String saleId, @RequestParam String userId) throws Exception {
        basicValidations(saleId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("sale",saleService.getSaleDTO(saleId)));
    }

    private void basicValidations(String saleId, String userId){
        GlobalValidator.checkIfTwoFieldsAreEmpty(saleId,userId);
        authService.checkFraudulentRequest(userId);
    }

}
