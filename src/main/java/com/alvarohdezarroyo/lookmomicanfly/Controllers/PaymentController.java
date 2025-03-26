package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PaymentDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Services.PaymentService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PaymentMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private final UserValidator userValidator;
    private final PaymentService paymentService;

    public PaymentController(UserValidator userValidator, PaymentService paymentService) {
        this.userValidator=userValidator;
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, PaymentDTO>> createPayment(@RequestParam String userId, @RequestParam Long amount) throws StripeException {
        GlobalValidator.checkIfTwoFieldsAreEmpty(userId,amount+"");
        final User user=userValidator.returnUserById(userId);
        final PaymentIntent intent= PaymentMapper.toPaymentIntent(amount,userId);
        Payment payment=PaymentMapper.toPayment(intent,user);
        payment=paymentService.savePayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("payment",
                        PaymentMapper.toDTO(payment,intent)));
    }

}
