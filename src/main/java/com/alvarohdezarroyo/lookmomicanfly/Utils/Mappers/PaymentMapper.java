package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PaymentDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private static PaymentIntentCreateParams toPaymentIntentParams(Long amount, String userId){
        try{
            Long.parseLong(amount+"");
            if(amount<0)
                throw new RuntimeException();
            return PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("eur")
                    .build();
        }
        catch (Exception e){
            throw new IllegalArgumentException("Invalid amount.");
        }
    }

    public static PaymentIntent toPaymentIntent(Long amount, String userId) throws StripeException {
        final PaymentIntentCreateParams params=toPaymentIntentParams(amount,userId);
        return PaymentIntent.create(params);
    }

    public static Payment toPayment(PaymentIntent intent, User user) throws StripeException {
        final Payment payment=new Payment();
        payment.setCurrency(intent.getCurrency());
        payment.setPaymentIntentId(intent.getId());
        payment.setStatus(intent.getStatus());
        payment.setUser(user);
        return payment;
    }

    public static PaymentDTO toDTO(Payment payment, PaymentIntent paymentIntent){
        return new PaymentDTO(paymentIntent.getClientSecret(),payment.getPaymentIntentId());
    }

}
