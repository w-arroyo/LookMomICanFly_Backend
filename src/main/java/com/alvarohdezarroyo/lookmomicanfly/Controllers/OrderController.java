package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.OrderDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.OrderService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;
    private final AuthService authService;

    public OrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @PutMapping("/update")
    public ResponseEntity<String> completeShippedOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.completeShippedOrders());
    }

    @GetMapping("/get-order/")
    public ResponseEntity<Map<String,OrderDTO>> getOrderDTO(@RequestParam String orderId, @RequestParam String userId) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(orderId,userId);
        authService.checkFraudulentRequest(userId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("order",orderService.getOrder(orderId)));
    }

}
