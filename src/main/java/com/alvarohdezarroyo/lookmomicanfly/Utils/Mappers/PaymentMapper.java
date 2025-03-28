package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PaymentDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private static PaymentIntentCreateParams toPaymentIntentParams(String userId){
        try{
            return PaymentIntentCreateParams.builder()
                    .setAmount(0L) // requires a Long type
                    .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL) // money will be taken later
                    .setCurrency("eur")
                    .putMetadata("userId",userId)
                    .build();
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static PaymentIntent toPaymentIntent(String userId) throws StripeException {
        final PaymentIntentCreateParams params=toPaymentIntentParams(userId);
        return PaymentIntent.create(params);
    }

    public static Payment toPayment(PaymentIntent intent){
        final Payment payment=new Payment();
        payment.setCurrency(intent.getCurrency());
        payment.setPaymentIntentId(intent.getId());
        payment.setStatus(intent.getStatus());
        return payment;
    }

    public static PaymentDTO toDTO(Payment payment, PaymentIntent paymentIntent){
        return new PaymentDTO(paymentIntent.getClientSecret(),payment.getPaymentIntentId());
    }

}
