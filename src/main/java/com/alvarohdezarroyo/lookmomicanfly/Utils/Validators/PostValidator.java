package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.RejectedPostException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.GetPostRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.PostRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostValidator {

    public static void checkIfPostFieldsAreEmpty(PostRequest postRequest){
        final List<String> emptyFields=new ArrayList<>();
        if(postRequest.getAddressId()==null || postRequest.getAddressId().trim().isEmpty())
            emptyFields.add("address");
        if(postRequest.getUserId()==null || postRequest.getUserId().trim().isEmpty())
            emptyFields.add("user");
        if(postRequest.getSize()==null || postRequest.getSize().trim().isEmpty())
            emptyFields.add("size");
        if(postRequest.getAmount()==null)
            emptyFields.add("amount");
        else{
            try {
                GlobalValidator.checkIfANumberFieldIsValid(postRequest.getAmount());
            }
            catch (IllegalArgumentException e){
                emptyFields.add("amount");
            }
        }
        if(postRequest instanceof BidRequest)
            checkIfBidFieldsAreEmpty(emptyFields,(BidRequest) postRequest);
        if(postRequest.getProductId()==null || postRequest.getProductId().trim().isEmpty())
            emptyFields.add("product");
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

    public static void checkIfBidFieldsAreEmpty(List<String> emptyFields, BidRequest bid){
        if(bid.getShippingOptionId()==null || bid.getShippingOptionId().trim().isEmpty())
            emptyFields.add("shipping option id");
    }

    public static void checkIfGetPostRequestFieldsAreEmpty(GetPostRequest request){
        if(request.getCategory()==null || request.getCategory().trim().isEmpty() || request.getProductId()==null || request.getProductId().trim().isEmpty() || request.getEntity()==null || request.getEntity().trim().isEmpty())
            throw new EmptyFieldsException("Empty fields are not allowed");
    }

    public static void checkIfEntityExists(String entity){
        if(!entity.equalsIgnoreCase("Bid") && !entity.equalsIgnoreCase("Ask"))
            throw new IllegalArgumentException(entity+" does not exist as entity.");
    }

    public static void checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(String requestUserId, String bestPostUserId){
        if(requestUserId.equals(bestPostUserId))
            throw new RejectedPostException("You are not allowed to create this transaction because it matches your own offer.");
    }

    public static boolean checkAskBeforeSavingIt(Ask ask, Bid bid){
        if(bid==null)
            return false;
        if(bid.getAmount()>ask.getAmount())
            throw new IllegalArgumentException("Ask amount can not be lower than highest bid amount.");
        else if(bid.getAmount()<ask.getAmount())
            return false;
        else{
            checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(ask.getUser().getId(),bid.getUser().getId());
            return true;
        }
    }

    public static boolean checkBidBeforeSavingIt(Bid bid, Ask ask){
        if(ask==null)
            return false;
        if(ask.getAmount()< bid.getAmount())
            throw new IllegalArgumentException("Bid amount can not surpass lowest ask amount.");
        else if(ask.getAmount()> bid.getAmount())
            return false;
        else{
            checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(ask.getUser().getId(),bid.getUser().getId());
            return true;
        }
    }

}
