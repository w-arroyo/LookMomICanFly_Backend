package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.OrderDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.TransactionOverviewDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;
    private final AuthService authService;
    private final TransactionMapper transactionMapper;

    public OrderController(OrderService orderService, AuthService authService, TransactionMapper transactionMapper) {
        this.orderService = orderService;
        this.authService = authService;
        this.transactionMapper = transactionMapper;
    }

    @PutMapping("/update")
    public ResponseEntity<String> completeShippedOrders(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.completeShippedOrders());
    }

    @GetMapping("/get/")
    public ResponseEntity<Map<String,OrderDTO>> getOrderDTO(@RequestParam String orderId, @RequestParam String userId) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(orderId,userId);
        authService.checkFraudulentRequest(userId);
        final Order order=orderService.getOrderById(orderId);
        PostValidator.checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(order.getBid().getUser().getId(),userId);
        final String tracking=orderService.getOrderTrackingNumber(order.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("order",
                transactionMapper.toOrderDTO(order,tracking)
        ));
    }

    @GetMapping("/get-all/")
    public ResponseEntity<Map<String,List<TransactionOverviewDTO>>> getAllOrders(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Order> orders=orderService.getAllUserOrders(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("orders",
                transactionMapper.ordersToOverview(orders)
                ));
    }

}
