package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.RejectedPostException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.BidRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.GetPostRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.PostRequestDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostValidator {

    public static void checkIfPostFieldsAreEmpty(PostRequestDTO postRequestDto){
        final List<String> emptyFields=new ArrayList<>();
        if(postRequestDto.getAddressId()==null || postRequestDto.getAddressId().trim().isEmpty())
            emptyFields.add("address");
        if(postRequestDto.getUserId()==null || postRequestDto.getUserId().trim().isEmpty())
            emptyFields.add("user");
        if(postRequestDto.getSize()==null || postRequestDto.getSize().trim().isEmpty())
            emptyFields.add("size");
        if(postRequestDto.getAmount()==null)
            emptyFields.add("amount");
        else{
            try {
                GlobalValidator.checkIfANumberFieldIsValid(postRequestDto.getAmount());
            }
            catch (IllegalArgumentException e){
                emptyFields.add("amount");
            }
        }
        if(postRequestDto instanceof BidRequestDTO)
            checkIfBidFieldsAreEmpty(emptyFields,(BidRequestDTO) postRequestDto);
        if(postRequestDto.getProductId()==null || postRequestDto.getProductId().trim().isEmpty())
            emptyFields.add("product");
        if(!emptyFields.isEmpty())
            throw new EmptyFieldsException(emptyFields);
    }

    public static void checkIfBidFieldsAreEmpty(List<String> emptyFields, BidRequestDTO bid){
        if(bid.getShippingOptionId()==null || bid.getShippingOptionId().trim().isEmpty())
            emptyFields.add("shipping option id");
    }

    public static void checkIfGetPostRequestFieldsAreEmpty(GetPostRequestDTO request){
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

    public static void checkBidBeforeSavingIt(Bid bid, Ask lowestAsk){
        if(lowestAsk==null)
            return;
        else if(bid.getAmount()>lowestAsk.getAmount())
            throw new IllegalArgumentException("Bid amount can not surpass lowest ask amount.");
        if(bid.getAmount()==lowestAsk.getAmount())
            checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(bid.getUser().getId(),lowestAsk.getUser().getId());
    }

    public static void checkAskBeforeSavingIt(Ask ask, Bid highestBid){
        if(highestBid==null)
            return;
        else if(ask.getAmount()<highestBid.getAmount())
            throw new IllegalArgumentException("Ask amount can not be lower than highest bid amount.");
        else if(ask.getAmount()==highestBid.getAmount())
            checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(ask.getUser().getId(),highestBid.getUser().getId());
    }

}
