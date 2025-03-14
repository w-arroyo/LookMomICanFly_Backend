package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AskDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Post;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
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

    @Autowired
    private final ProductService productService;
    private final UserValidator userValidator;

    public PostMapper(Double sellingShippingFee, ProductService productService, UserValidator userValidator) {
        this.sellingShippingFee = sellingShippingFee;
        this.productService = productService;
        this.userValidator = userValidator;
    }

    private void fillPostFields(PostDTO postDTO, Post post){
        post.setActive(true);
        post.setFinalized(false);
        post.setUser(userValidator.returnUserById(postDTO.getUserId()));
        post.setProduct(productService.findProductById(postDTO.getProductId()));
        post.setAmount(postDTO.getAmount());
    }

    private void setSellerFee(Ask ask){

    }

    public Ask toAsk(AskDTO askDTO){
        final Ask ask=new Ask();
        fillPostFields(askDTO,ask);
        ask.setShippingFee(sellingShippingFee);

        return ask;
    }

}
