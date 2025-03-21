package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Post;
import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.PostRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SellingFeeService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ShippingOptionService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    @Value("${app.sellingShippingFee}")
    private Double sellingShippingFee;
    @Value("${app.operationalFee}")
    private Double operationalFee;

    @Autowired
    private final ProductService productService;
    private final UserValidator userValidator;
    private final AddressService addressService;
    private final SellingFeeService sellingFeeService;
    private final ShippingOptionService shippingOptionService;

    public PostMapper(ProductService productService, UserValidator userValidator, AddressService addressService, SellingFeeService sellingFeeService, ShippingOptionService shippingOptionService) {
        this.productService = productService;
        this.userValidator = userValidator;
        this.addressService = addressService;
        this.sellingFeeService = sellingFeeService;
        this.shippingOptionService = shippingOptionService;
    }

    private void fillPostFields(PostRequest postRequest, Post post){
        post.setActive(true);
        post.setFinalized(false);
        post.setUser(userValidator.returnUserById(postRequest.getUserId()));
        post.setProduct(productService.findProductById(postRequest.getProductId()));
        post.setAddress(addressService.getAddressById(postRequest.getAddressId()));
        post.setAmount(postRequest.getAmount());
        post.setSize(ProductValidator.checkIfASizeExists(postRequest.getSize()));
    }

    public PostSummaryDTO toSummaryDTO(Post post){
        return new PostSummaryDTO(post.getId(),productService.findProductById(post.getProduct().getId()).getName(),post.getSize().getValue(),post.getAmount());
    }

    private void fillPostDTOFields(PostDTO postDTO, Post post) throws Exception {
        postDTO.setId(post.getId());
        postDTO.setSize(post.getSize().getValue());
        postDTO.setAmount(post.getAmount());
        postDTO.setAddressDTO(AddressMapper.toDTO(post.getAddress()));
        postDTO.setProductSummaryDTO(ProductMapper.toSummary(post.getProduct()));
    }

    public Ask toAsk(PostRequest askRequest){
        final Ask ask=new Ask();
        fillPostFields(askRequest,ask);
        ask.setShippingFee(sellingShippingFee);
        ask.setSellingFee(sellingFeeService.checkIfThereIsADefaultFee(askRequest.getUserId()));
        return ask;
    }

    public Bid toBid(BidRequest bidRequest){
        final Bid bid=new Bid();
        fillPostFields(bidRequest,bid);
        bid.setOperationalFee(operationalFee);
        bid.setShippingOption(shippingOptionService.getShippingOptionById(bidRequest.getShippingOptionId()));
        return bid;
    }

    public AskDTO toAskDTO(Ask ask) throws Exception {
        final AskDTO askDTO=new AskDTO();
        fillPostDTOFields(askDTO,ask);
        askDTO.setShippingFee(ask.getShippingFee());
        askDTO.setSellingFee(SellingFeeMapper.toDTO(ask.getSellingFee()));
        return askDTO;
    }

    public BidDTO toBidDTO(Bid bid) throws Exception {
        final BidDTO bidDTO=new BidDTO();
        fillPostDTOFields(bidDTO,bid);
        bidDTO.setShippingOptionDTO(ShippingOptionMapper.toDTO(bid.getShippingOption()));
        bidDTO.setOperatingFee(bid.getOperationalFee());
        return bidDTO;
    }

    public HighestLowestPostDTO toHighestLowestPostDTO(Post post, Size size){
        final HighestLowestPostDTO lowestPostDTO=new HighestLowestPostDTO();
        lowestPostDTO.setSize(size.getValue());
        if(post==null)
            lowestPostDTO.setAmount("-");
        else lowestPostDTO.setAmount(post.getAmount()+"");
        return lowestPostDTO;
    }

}
