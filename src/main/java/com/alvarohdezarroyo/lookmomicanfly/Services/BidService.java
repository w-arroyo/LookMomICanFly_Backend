package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

    @Autowired
    private final BidRepository bidRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public Bid saveBid(Bid bid){
        return bidRepository.save(bid);
    }

    public Bid findBidById(String id){
        return bidRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Bid ID does not exist.")
        );
    }

    public Bid getHighestBid(String productId, Size size){
        return bidRepository.getHighestBid(productId,size).orElse(null);
    }

    public Integer getHighestBidAmount(String productId, Size size){
        return bidRepository.getHighestBidAmount(productId,size.getValue());
    }

}
