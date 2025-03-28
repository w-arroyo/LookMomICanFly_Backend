package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PaymentDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PaymentService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PaymentMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${app.stripeSecretKey}")
    private String stripeSecretKey;

    @Autowired
    private final PaymentService paymentService;
    private final AuthService authService;

    public PaymentController(PaymentService paymentService, AuthService authService) {
        this.paymentService = paymentService;
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, PaymentDTO>> createPayment(@RequestParam String userId) throws StripeException {
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final PaymentIntent intent= PaymentMapper.toPaymentIntent(userId);
        Payment payment=PaymentMapper.toPayment(intent);
        payment=paymentService.savePayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("payment",
                        PaymentMapper.toDTO(payment,intent)));
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> handlePayment(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) throws SignatureVerificationException {
        final Event event= Webhook.constructEvent(payload,signature,stripeSecretKey); // method throws SignatureVerificationException exception
        final PaymentIntent paymentIntent = getPaymentIntent(event);
        System.out.println(paymentIntent.getStatus());
        final Payment updatedPayment=paymentService.updatePayment(paymentIntent);
        System.out.println(updatedPayment.getStatus());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        "ID: "+updatedPayment.getId()+". Status: "+updatedPayment.getStatus()));
    }

    private PaymentIntent getPaymentIntent(Event event) {
        final EventDataObjectDeserializer deserializer= event.getDataObjectDeserializer();
        final PaymentIntent paymentIntent= (PaymentIntent) deserializer.getObject().orElse(null); // getObject() returns StripeObject so you got to cast it
        // you can not create a class to map the event because not all the events Stripe sends are the same so they don't always have the same attributes
        if(paymentIntent==null)
            throw new RuntimeException("Unable to deserialize Payment Intent.");
        return paymentIntent;
    }

}
