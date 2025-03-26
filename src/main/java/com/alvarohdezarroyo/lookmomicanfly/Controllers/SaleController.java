package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SaleDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.TransactionOverviewDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SaleService;
import com.alvarohdezarroyo.lookmomicanfly.Services.TransactionStatusService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private final TransactionStatusService transactionStatusService;
    private final AuthService authService;
    private final SaleService saleService;
    private final TransactionMapper transactionMapper;

    public SaleController(TransactionStatusService transactionStatusService, AuthService authService, SaleService saleService, TransactionMapper transactionMapper) {
        this.transactionStatusService = transactionStatusService;
        this.authService = authService;
        this.saleService = saleService;
        this.transactionMapper = transactionMapper;
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

    @GetMapping("/get/")
    public ResponseEntity<Map<String,SaleDTO>> getSaleDTO(@RequestParam String saleId, @RequestParam String userId) throws Exception {
        basicValidations(saleId,userId);
        final Sale sale=saleService.getSaleById(saleId);
        GlobalValidator.checkIfDataBelongToRequestingUser(userId,sale.getAsk().getUser().getId());
        final String tracking=saleService.getSaleCurrentTrackingNumberCode(sale.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("sale",
                        transactionMapper.toSaleDTO(sale,tracking)));
    }

    @GetMapping("/get-all/")
    public ResponseEntity<Map<String,List<TransactionOverviewDTO>>> getAllUserSales(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Sale> sales=saleService.getAllUserSales(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
          Map.of("sales",
                  transactionMapper.salesToOverview(sales))
        );
    }

    private void basicValidations(String saleId, String userId){
        GlobalValidator.checkIfTwoFieldsAreEmpty(saleId,userId);
        authService.checkFraudulentRequest(userId);
    }

}
