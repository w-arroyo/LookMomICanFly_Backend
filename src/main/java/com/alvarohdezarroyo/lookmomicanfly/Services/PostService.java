package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.HighestLowestPostDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Post;
import com.alvarohdezarroyo.lookmomicanfly.Models.Transaction;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PostRepository;
import com.alvarohdezarroyo.lookmomicanfly.Requests.AskRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PostMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.AddressValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public void completePost(String id){
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

    private Ask getLowestAsk(String productId, Size size){
        return askService.getLowestAsk(productId, size);
    }

    @Transactional
    public Map<String,Object> saveBidFromRequest(BidRequest bidRequest) throws Exception {
        final Bid bid= postMapper.toBid(bidRequest);
        AddressValidator.checkIfAddressBelongsToAUser(bidRequest.getUserId(), bid.getAddress());
        ProductValidator.checkIfSizeBelongsToACategory(bid.getSize(),bid.getProduct().getCategory());
        final Ask ask=getLowestAsk(bid.getProduct().getId(),bid.getSize());
        if(!PostValidator.checkBidBeforeSavingIt(bid,ask))
            return Map.of("bid",postMapper.toBidDTO(bidService.saveBid(bid)));
        bid.setId(bidService.saveBid(bid).getId());
        return Map.of("order",TransactionMapper.orderToDTO(completeTransaction(ask,bid).getOrder()));
    }

    @Transactional
    public Map<String,Object> saveAskFromRequest(AskRequest askRequest) throws Exception {
        final Ask ask= postMapper.toAsk(askRequest);
        ProductValidator.checkIfSizeBelongsToACategory(ask.getSize(),ask.getProduct().getCategory());
        AddressValidator.checkIfAddressBelongsToAUser(askRequest.getUserId(), ask.getAddress());
        final Bid bid=getHighestBid(ask.getProduct().getId(),ask.getSize());
        if(!PostValidator.checkAskBeforeSavingIt(ask,bid))
            return Map.of("ask",postMapper.toAskDTO(askService.saveAsk(ask)));
        ask.setId(askService.saveAsk(ask).getId());
        return Map.of("sale",TransactionMapper.saleToDTO(completeTransaction(ask,bid).getSale()));
    }

    private Bid getHighestBid(String productId, Size size){
        return bidService.getHighestBid(productId, size);
    }

    @Transactional
    private Transaction completeTransaction(Ask ask, Bid bid){
        completeBidAndAsk(bid.getId(),ask.getId());
        return transactionService.saveTransaction(transactionService.createOrder(bid),transactionService.createSale(ask));
    }

    @Transactional
    private void completeBidAndAsk(String bidId, String askId){
        completePost(askId);
        completePost(bidId);
    }

}
