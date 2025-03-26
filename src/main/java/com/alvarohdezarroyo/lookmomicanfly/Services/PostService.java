package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PostRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.AddressValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final AskService askService;
    private final BidService bidService;
    private final MatchingPostsService matchingPostsService;

    public PostService(PostRepository postRepository, AskService askService, BidService bidService, MatchingPostsService matchingPostsService) {
        this.postRepository = postRepository;
        this.askService = askService;
        this.bidService = bidService;
        this.matchingPostsService = matchingPostsService;
    }

    @Transactional
    public void deactivatePost(String id, String userId){
        if(postRepository.deactivatePost(id,userId)<1)
            throw new FraudulentRequestException("Forbidden request. You can't manipulate other user's data.");
    }

    @Transactional
    private void completePost(String id){
        if(postRepository.completePost(id)<1)
            throw new RuntimeException("Server error.");
    }

    public Integer getASizeBestPost(String id, String entity, Size size){
        if(entity.equals("ask"))
            return askService.getLowestAsk(id,size).getAmount();
        return bidService.getHighestBid(id,size).getAmount();
    }

    @Transactional
    public Object saveBid(Bid bid){
        PostValidator.checkBidAmountIsPositive(bid);
        AddressValidator.checkIfAddressBelongsToAUser(bid.getUser().getId(),bid.getAddress());
        ProductValidator.checkIfSizeBelongsToACategory(bid.getSize(),bid.getProduct().getCategory());
        final Ask lowestAsk=askService.getLowestAsk(bid.getProduct().getId(),bid.getSize());
        PostValidator.checkBidBeforeSavingIt(bid,lowestAsk);
        final Bid savedBid= bidService.saveBid(bid);
        return checkMatchingAsk(savedBid,lowestAsk);
    }

    @Transactional
    public Object updateBid(String bidId, Integer amount, String userId){
        final Bid foundBid= bidService.findBidById(bidId);
        foundBid.setAmount(amount);
        PostValidator.checkIfPostBelongToUser(userId,foundBid.getUser().getId());
        return saveBid(foundBid);
    }

    @Transactional
    public Object updateAsk(String askId, Integer amount, String userId){
        final Ask foundAsk=askService.findAskById(askId);
        foundAsk.setAmount(amount);
        PostValidator.checkIfPostBelongToUser(userId,foundAsk.getUser().getId());
        return saveAsk(foundAsk);
    }

    @Transactional
    private Object checkMatchingAsk(Bid savedBid, Ask lowestAsk){
        if(lowestAsk==null || lowestAsk.getAmount()> savedBid.getAmount()){
            return savedBid;
        }
        completeMatchingPosts(lowestAsk,savedBid);
        return matchingPostsService.saveTransactionAndGetOrder(savedBid,lowestAsk);
    }

    @Transactional
    public Object saveAsk(Ask ask){
        PostValidator.checkAskAmountIsPositive(ask);
        ProductValidator.checkIfSizeBelongsToACategory(ask.getSize(),ask.getProduct().getCategory());
        AddressValidator.checkIfAddressBelongsToAUser(ask.getUser().getId(), ask.getAddress());
        final Bid highestBid=bidService.getHighestBid(ask.getProduct().getId(),ask.getSize());
        PostValidator.checkAskBeforeSavingIt(ask,highestBid);
        final Ask savedAsk=askService.saveAsk(ask);
        return checkForMatchingBid(savedAsk,highestBid);
    }

    @Transactional
    private Object checkForMatchingBid(Ask savedAsk, Bid highestBid){
        if(highestBid==null || savedAsk.getAmount()> highestBid.getAmount()){
            return savedAsk;
        }
        completeMatchingPosts(savedAsk,highestBid);
        return matchingPostsService.saveTransactionAndGetSale(savedAsk,highestBid);
    }

    @Transactional
    private void completeMatchingPosts(Ask ask, Bid bid){
        completePost(ask.getId());
        completePost(bid.getId());
    }


}
