package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SellingFeeDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.SellingFeeService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.SellingFeeMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    @Autowired
    private final SellingFeeService sellingFeeService;

    public FeeController(SellingFeeService sellingFeeService) {
        this.sellingFeeService = sellingFeeService;
    }

    @GetMapping("/level/")
    public ResponseEntity<SellingFeeDTO> getUserLevelSellingFee(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        return ResponseEntity.status(HttpStatus.OK).body(SellingFeeMapper.toDTO(sellingFeeService.selectFeeByNumberSales(sellingFeeService.returnUserNumberSalesDuringPastThreeMonths(userId))));
    }

    @GetMapping("/default/")
    public ResponseEntity<SellingFeeDTO> getCurrentSellingFee(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        return ResponseEntity.status(HttpStatus.OK).body(SellingFeeMapper.toDTO(sellingFeeService.checkIfThereIsADefaultFee(userId)));
    }

    @PostMapping("/save-default")
    public ResponseEntity<Map<String,Object>> saveNewDefaultSellingFee(@RequestBody SellingFeeDTO sellingFeeDTO){
        GlobalValidator.checkIfRequestBodyIsEmpty(sellingFeeDTO);
        GlobalValidator.checkIfAFieldIsEmpty(sellingFeeDTO.getDescription());
        GlobalValidator.checkIfANumberFieldIsValid(sellingFeeDTO.getPercentage().intValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",sellingFeeService.saveSellingFeeOffer(SellingFeeMapper.toSellingFee(sellingFeeDTO))));
    }

}
