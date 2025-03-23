package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.HighestLowestPostDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PostRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.AddressValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AskService askService;
    private final BidService bidService;


    private final TransactionService transactionService;

    public PostService(PostRepository postRepository, PostMapper postMapper, AskService askService, BidService bidService, TransactionService transactionService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.askService = askService;
        this.bidService = bidService;
        this.transactionService = transactionService;
    }

    @Transactional
    public String deactivatePost(String id, String userId){
        if(postRepository.deactivatePost(id,userId)<1)
            throw new FraudulentRequestException("Forbidden request. You can't manipulate other user's data.");
        return "success";
    }

    @Transactional
    private void completePost(String id){
        if(postRepository.completePost(id)<1)
            throw new RuntimeException("Server error.");
    }

    public PostSummaryDTO[] getAllUserActivePosts(String userId, String table){
        List<Post> posts;
        if(table.equals("Bid"))
            posts=postRepository.getAllUserActiveBids(userId);
        else posts=postRepository.getAllUserActiveAsks(userId);
        if(posts.isEmpty())
            return new PostSummaryDTO[0];
        final PostSummaryDTO[] summary=new PostSummaryDTO[posts.size()];
        for (int post = 0; post < summary.length; post++) {
            summary[post]=postMapper.toSummaryDTO(posts.get(post));
        }
        return summary;
    }

    public HighestLowestPostDTO[] getAllHighestLowestPost(String productId, ProductCategory category, String table){
        final List<Size> sizes= ProductValidator.getSizesByCategory(category);
        final HighestLowestPostDTO[] lowestHighest= new HighestLowestPostDTO[sizes.size()];
        int position=0;
        for(Size size: sizes){
            if(table.equalsIgnoreCase("bid"))
                lowestHighest[position]=postMapper.toHighestLowestPostDTO(bidService.getHighestBid(productId,size),size);
            else lowestHighest[position]=postMapper.toHighestLowestPostDTO(askService.getLowestAsk(productId,size),size);
        }
        return lowestHighest;
    }

    public Integer getASizeBestPost(String id, String entity, Size size){
        if(entity.equals("ask"))
            return askService.getLowestAsk(id,size).getAmount();
        return bidService.getHighestBid(id,size).getAmount();
    }

    @Transactional
    public Object saveBidFromRequest(Bid bid){
        AddressValidator.checkIfAddressBelongsToAUser(bid.getUser().getId(),bid.getAddress());
        ProductValidator.checkIfSizeBelongsToACategory(bid.getSize(),bid.getProduct().getCategory());
        final Ask lowestAsk=askService.getLowestAsk(bid.getProduct().getId(),bid.getSize());
        PostValidator.checkIfBidIsHigherThanLowestAsk(bid.getAmount(),lowestAsk.getAmount());
        final Bid savedBid= bidService.saveBid(bid);
        if(!MatchingPostsService.checkForMatchingAsk(savedBid,lowestAsk)){
            return savedBid;
        }
        completeMatchingPosts(lowestAsk,savedBid);
        return transactionService
                .createOrderSaleAndTransaction(lowestAsk,savedBid)
                .getOrder();
    }

    @Transactional
    public Object saveAskFromRequest(Ask ask){
        ProductValidator.checkIfSizeBelongsToACategory(ask.getSize(),ask.getProduct().getCategory());
        AddressValidator.checkIfAddressBelongsToAUser(ask.getUser().getId(), ask.getAddress());
        final Bid highestBid=bidService.getHighestBid(ask.getProduct().getId(),ask.getSize());
        PostValidator.checkIfAskIsLowerThanHighestBid(ask.getAmount(),highestBid.getAmount());
        final Ask savedAsk=askService.saveAsk(ask);
        if(!MatchingPostsService.checkForMatchingBid(savedAsk,highestBid)){
            return savedAsk;
        }
        completeMatchingPosts(savedAsk,highestBid);
        return transactionService
                .createOrderSaleAndTransaction(savedAsk,highestBid)
                .getSale();
    }

    @Transactional
    private void completeMatchingPosts(Ask ask, Bid bid){
        completePost(ask.getId());
        completePost(bid.getId());
    }


}
