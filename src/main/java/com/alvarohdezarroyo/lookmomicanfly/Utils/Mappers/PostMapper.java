package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AskDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.BidDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.Size;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Post;
import com.alvarohdezarroyo.lookmomicanfly.Requests.AskRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.BidRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.PostRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Services.SellingFeeService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ShippingOptionService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class PostMapper {

    @Value("${app.sellingShippingFee}")
    private final Double sellingShippingFee;
    @Value("${app.operationalFee}")
    private final Double operationalFee;

    @Autowired
    private final ProductService productService;
    private final UserValidator userValidator;
    private final AddressService addressService;
    private final SellingFeeService sellingFeeService;
    private final ShippingOptionService shippingOptionService;

    public PostMapper(Double sellingShippingFee, Double operationalFee, ProductService productService, UserValidator userValidator, AddressService addressService, SellingFeeService sellingFeeService, ShippingOptionService shippingOptionService) {
        this.sellingShippingFee = sellingShippingFee;
        this.operationalFee = operationalFee;
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
        post.setSize(Size.checkIfASizeExists(postRequest.getSize()));
    }

    private void fillPostDTOFields(PostDTO postDTO, Post post) throws Exception {
        postDTO.setId(post.getId());
        postDTO.setSize(post.getSize().getValue());
        postDTO.setAmount(post.getAmount());
        postDTO.setAddressDTO(AddressMapper.toDTO(post.getAddress()));
        postDTO.setProductSummaryDTO(ProductMapper.toSummary(post.getProduct()));
    }

    public Ask toAsk(AskRequest askRequest){
        final Ask ask=new Ask();
        fillPostFields(askRequest,ask);
        ask.setShippingFee(sellingShippingFee);
        ask.setSellingFee(sellingFeeService.getSellingFeeById(askRequest.getSellingFeeId()));
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

}
